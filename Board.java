
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
private List<Cell> emptyCells;
private Scanner scanner;
private Player[][] board;
private List<Cell> rootValues;

public Board(){
	initializeBoard();
}
 
private void initializeBoard(){
	this.rootValues=new ArrayList<>();
	this.scanner=new Scanner(System.in);
	this.board=new Player[Constant.BOARD_SIZE][Constant.BOARD_SIZE];
}
public boolean isRunning(){
	if(isWinning(Player.COMPUTER)) return false;
	if(isWinning(Player.USER)) return false;
	if(getEmptyCells().isEmpty()) return false;
	return true;
}

public List<Cell> getEmptyCells() {
	emptyCells=new ArrayList<>();
	for(int i=0;i<Constant.BOARD_SIZE;i++){
		for(int j=0;j<Constant.BOARD_SIZE;j++){
			if(board[i][j]==Player.NONE){
				emptyCells.add(new Cell(i,j));
			}
		}
	}
	return emptyCells;
}

public boolean isWinning(Player player){
	int k=3,h=1;
	int flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0;
	for(int i=0;i<3;i++){
		if(board[i][i]!=player)
			flag1=1;
		if(board[i][k--]!=player)
			flag2=1;
		if(board[i][h++]!=player)
			flag3=1;
	}
	h=2;k=0;
	for(int i=1;i<Constant.BOARD_SIZE;i++){
		if(board[i][i]!=player)
			flag4=1;
		if(board[i][k++]!=player)
			flag5=1;
		if(board[i][h--]!=player)
			flag6=1;
	}
	if(flag1==0||flag2==0||flag3==0||flag4==0||flag5==0||flag6==0)
		return true;
/*	if(board[0][0]==player&&board[1][1]==player&&board[2][2]==player)
         return true; 
	
	if(board[0][2]==player&&board[1][1]==player&&board[2][0]==player)
        return true; */
	for(int i=0;i<Constant.BOARD_SIZE;i++){
		if(board[i][0]==player&&board[i][1]==player&&board[i][2]==player)
	         return true; 
		if(board[0][i]==player&&board[1][i]==player&&board[2][i]==player)
	         return true; 
	}
	for(int i=0;i<Constant.BOARD_SIZE;i++){
		if(board[i][1]==player&&board[i][2]==player&&board[i][3]==player)
	         return true; 
		if(board[1][i]==player&&board[2][i]==player&&board[3][i]==player)
	         return true; 
	}
	return false;
}
public void move(Cell cell, Player player){
	this.board[cell.getX()][cell.getY()]=player;
}
public Cell getBestMove(){
	int max=Integer.MIN_VALUE;
	int best=Integer.MIN_VALUE;
	for(int i=0;i<rootValues.size();i++){
		if(max<rootValues.get(i).getMinimaxValue() ){
			max=rootValues.get(i).getMinimaxValue();
			best=i;
		}
	}
	
	move (rootValues.get(best), Player.COMPUTER);
	if(isWinning(Player.COMPUTER)){
		move (rootValues.get(best), Player.NONE);
		return rootValues.get(best);
	}
	else{
		move (rootValues.get(best), Player.NONE);
		int k=(int)(Math.random()*(rootValues.size()));
		move (rootValues.get(k), Player.COMPUTER);
		int c=0;
	while(isWinning(Player.COMPUTER)==false&&c<rootValues.size()){
		move (rootValues.get(k), Player.NONE);
		k=(int)(Math.random()*(rootValues.size()));
		move (rootValues.get(k), Player.COMPUTER);
		c++;
	}
	if(isWinning(Player.COMPUTER))
	best=k;
	move (rootValues.get(k), Player.NONE);
	return rootValues.get(best);
	}
}

public void makeUserInput(){
	System.out.println("User's move:");
	int x=scanner.nextInt();
	int y=scanner.nextInt();
	if(x>=0&&x<Constant.BOARD_SIZE&&y>=0&&y<Constant.BOARD_SIZE){
	if(board[x][y]==Player.NONE){
	Cell cell = new Cell (x,y);
	move (cell, Player.USER);
	}
	else{
		System.out.println("Wrong input...\nplease enter the index of empty cell");
		makeUserInput();
	}
	}
	else{
		System.out.println("Wrong input...\nplease enter a valid index of cell");
		makeUserInput();
	}
}

public void displayBoard(){
	System.out.println();
	for(int i=0;i<Constant.BOARD_SIZE;i++){
		for(int j=0;j<Constant.BOARD_SIZE;j++){
			System.out.print(board[i][j]+" ");
		}
		System.out.println();
	}
}

public int returnMin(List<Integer>list){
	int min = Integer.MAX_VALUE;
	int index= Integer.MIN_VALUE;
	for(int i=0;i<list.size();i++){
		if(list.get(i)<min){
			min=list.get(i);
			index=i;
		}
	}
	return list.get(index);  // return min;
}

public int returnMax(List<Integer>list){
	int max = Integer.MIN_VALUE;
	int index= Integer.MIN_VALUE;
	for(int i=0;i<list.size();i++){
		if(list.get(i)>max){
			max=list.get(i);
			index=i;
		}
	}
	return list.get(index);  // return max;
}

public void callMinimax(int depth, Player player,int count){
	rootValues.clear();
	minimax(depth,player,count);
}

private int minimax(int depth, Player player,int count){

	if(isWinning(Player.COMPUTER)) return +1;
	if(isWinning(Player.USER)) return -1;
	List<Cell> availableCells=getEmptyCells();
	if(availableCells.isEmpty()) return 0;
	
		if(depth>=count)
			return 0;
	List<Integer> scores = new ArrayList();
	
	for(int i=0;i<availableCells.size();i++){
		Cell point =availableCells.get(i);
		if(player==Player.COMPUTER){
			move(point, Player.COMPUTER);
			int currentScore=minimax(depth+1,Player.USER,count);
			scores.add(currentScore);
			if(depth==0){
				point.setMinimaxValue(currentScore);
				rootValues.add(point);
			}
		}
			else if(player==Player.USER){
				move(point,Player.USER);
				scores.add(minimax(depth+1,Player.COMPUTER,count));
			}
			board[point.getX()][point.getY()]=Player.NONE;
		}
		if(player==Player.COMPUTER){
			return returnMax(scores);
		}
		return returnMin(scores);
}

public void setupBoard(){
	for(int i=0;i<Constant.BOARD_SIZE;i++){
		for(int j=0;j<Constant.BOARD_SIZE;j++){
			board[i][j]=Player.NONE;
		}
	}
}

public Scanner getScanner() {
	return scanner;
}

public void setScanner(Scanner scanner) {
	this.scanner = scanner;
}

public Player[][] getBoard() {
	return board;
}

public void  setBoard(Player[][] board) {
	this.board = board;
}

public List<Cell> getRootValues() {
	return rootValues;
}

public void setRootValues(List<Cell> rootValues) {
	this.rootValues = rootValues;
}

public void setEmptyCells(List<Cell> emptyCells) {
	this.emptyCells = emptyCells;
}

}
