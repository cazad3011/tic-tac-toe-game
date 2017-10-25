
import java.util.Random;

public class Game {
private Board board;
private Random random;
public Game(){
	initializeGame();
	displayBoard();
	makeFirstMove();
	playGame();
	checkStatus();
}
private void initializeGame() {
	this.board=new Board();
	this.board.setupBoard();
	this.random=new Random();
	
}
private void playGame() {
	int count=6;
	while(this.board.isRunning()){
		/*System.out.println("User move: ");
		Cell userCell =new Cell(board.getScanner().nextInt(),board.getScanner().nextInt());
		this.board.move(userCell, Player.USER);*/
	
		board.makeUserInput();
	displayBoard();
	if(!this.board.isRunning()){
		break;
	}
	System.out.println("Computer is thinking.....");
	this.board.callMinimax(0, Player.COMPUTER,count);
	count+=3;
	/*for(Cell cell : this.board.getRootValues()){
		System.out.println("Cell value: "+cell+"minimax value "+cell.getMinimaxValue());
	}*/
	this.board.move(board.getBestMove(), Player.COMPUTER);
	displayBoard();
	}
	
}
private void makeFirstMove(){
	System.out.println("Who starts? \n1 - Computer\n2 - User");
	int choice=board.getScanner().nextInt();
	if(choice==1){
		System.out.println("Computer is thinking.....");
		Cell cell =new Cell(random.nextInt(Constant.BOARD_SIZE),random.nextInt(Constant.BOARD_SIZE));
	    this.board.move(cell, Player.COMPUTER);
	    displayBoard();
	}
	
}
private void displayBoard(){
	this.board.displayBoard();
}
private void checkStatus(){
	if(board.isWinning(Player.COMPUTER))
		System.out.println("Computer has won!");
	else if(board.isWinning(Player.USER))
		System.out.println("User has won!");
	else
		System.out.println("Game Draw!");
	int n=3;
	while(n!=1&&n!=2){
	System.out.println("1: Restart Game\n2: Exit Game\nEnter a valid choice");
	    n=board.getScanner().nextInt();
	}
	if(n==1)
	new Game();
	else{
		System.out.println("Normal exiting...");
	System.exit(0);
	}
	}
}

