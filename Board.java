package codes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Board{
	
	/*The Sudoku Board is made of 9x9 cells for a total of 81 cells.
	 * In this program we will be representing the Board using a 2D Array of cells.
	 * 
	 */

	private Cell[][] board = new Cell[9][9];
	
	
	//The variable "level" records the level of the puzzle being solved.
	private String level = "";

	
	///TODO: CONSTRUCTOR
	//This must initialize every cell on the board with a generic cell.  It must also assign all of the boxIDs to the cells
	public Board()
	{
		for(int x = 0; x < 9; x++)
			for(int y = 0 ; y < 9; y++)
			{
				board[x][y] = new Cell();
				board[x][y].setBoxID( 3*(x/3) + (y)/3+1);
			}
	}
	

	
	///TODO: loadPuzzle
	/*This method will take a single String as a parameter.  The String must be either "easy", "medium" or "hard"
	 * If it is none of these, the method will set the String to "easy".  The method will set each of the 9x9 grid
	 * of cells by accessing either "easyPuzzle.txt", "mediumPuzzle.txt" or "hardPuzzle.txt" and setting the Cell.number to 
	 * the number given in the file.  
	 * 
	 * This must also set the "level" variable
	 * TIP: Remember that setting a cell's number affects the other cells on the board.
	 */
	
	public void loadPuzzle(String level) throws Exception
	{
		this.level = level;
		String fileName = "easyPuzzle.txt";
		if(level.contentEquals("medium"))
			fileName = "mediumPuzzle.txt";
		else if(level.contentEquals("hard"))
			fileName = "hardPuzzle.txt";
		else if(level.contentEquals("logic1test1"))
			fileName="logic1test1.txt";
		else if(level.contentEquals("logic1test2"))
			fileName="logic1test2.txt";
		else if(level.contentEquals("logic1test3"))
			fileName="logic1test3.txt";
		else if(level.contentEquals("logic2test1"))
			fileName="logic2test1.txt";
		else if(level.contentEquals("logic3test1"))
			fileName="logic3test1.txt";
		else if(level.contentEquals("logic4test1"))
			fileName="logic4test1.txt";
		
		
		Scanner input = new Scanner (new File(fileName));
		
		for(int x = 0; x < 9; x++)
			for(int y = 0 ; y < 9; y++)
			{
				int number = input.nextInt();
				if(number != 0)
					solve(x, y, number);
			}
						
		input.close();
		
	}
	
	
	///TODO: isSolved
	/*This method scans the board and returns TRUE if every cell has been solved.  Otherwise it returns FALSE
	 * 
	 */
	public boolean isSolved()
	{
		for(int x=0;x<board.length;x++)
			for(int y=0;y<board[0].length;y++)
				if(board[x][y].getNumber()==0)
					return false;
		return true;
		/*
		int sumx=0;
		int[] sumy= {0,0,0,0,0,0,0,0,0};
		int[] sumBox= {0,0,0,0,0,0,0,0,0};
		for(int y=0;y<9;y++) {
			for(int x=0;x<9;x++) {
				if(board[y][x].getNumber()==0)
					return false;
				sumx+=board[y][x].getNumber();
				sumy[x]+=board[y][x].getNumber();
				sumBox[3*(y/3) + (x)/3+1]+=board[y][x].getNumber();
				}
			if(sumx!=45)
				return false;
			}
		for(int x=0;x<9;x++)
			if(!(sumy[x]==sumBox[x]&&sumy[x]==45))
				return false;
		return true;*/
		
			
	}


	///TODO: DISPLAY
	/*This method displays the board neatly to the screen.  It must have dividing lines to show where the box boundaries are
	 * as well as lines indicating the outer border of the puzzle
	 */
	public void display()
	{
		int number=0;
		
		System.out.println("################################################");
		for(int x=0;x<board.length;x++) {
			for(int y=0;y<board.length;y++) {
				number=board[x][y].getNumber();
				if(y==0)
					System.out.print("# "+number+" |");
				else if(y%3==2)
					System.out.print("  "+number+"  #");
				else
					System.out.print("  "+number+" |");
				
					
			}
		System.out.println();
		if(x%3==2)
			System.out.println("################################################");
		else
			System.out.println("#--------------#---------------#---------------#");
		}
		System.out.println();
	}
	
	public void displayPotentials() {
int number=0;
		System.out.println("Potentials");
		System.out.println("################################################");
		for(int x=0;x<board.length;x++) {
			for(int y=0;y<board[0].length;y++) {
				number=board[x][y].numberOfPotentials();
				if(y==0)
					System.out.print("# "+number+" |");
				else if(y%3==2)
					System.out.print("  "+number+"  #");
				else
					System.out.print("  "+number+" |");
				
					
			}
		System.out.println();
		if(x%3==2)
			System.out.println("################################################");
		else
			System.out.println("#--------------#---------------#---------------#");
		}
		System.out.println();
	}
	///TODO: solve
	/*This method solves a single cell at x,y for number.  It also must adjust the potentials of the remaining cells in the same row,
	 * column, and box.
	 */
	public void solve(int x, int y, int number)
	{
		
		board[x][y].setNumber(number);
		for(int i=0;i<board.length;i++) {
			/*
			potential= board[i][y].getPotential();
			potential[number]=false;
			board[i][y].setPotential(potential);*/
			if(x!=i)
				board[i][y].cantBe(number);
		}
		for(int j=0;j<board[0].length;j++) {/*
			potential= board[x][j].getPotential();
			potential[number]=false;
			board[x][j].setPotential(potential);*/
			if(j!=y)
				board[x][j].cantBe(number);
		}
		int id=board[x][y].getBoxID();
		int i=((id-1)/3)*3;
		int j=3*((id-1)%3);
		for(int boxX=i;boxX<i+3;boxX++)
			for(int boxY=j;boxY<j+3;boxY++) {/*
				potential= board[boxX][boxY].getPotential();
				potential[number]=false;
				board[boxX][boxY].setPotential(potential);*/
				if(boxX!=x&&boxY!=y)
					board[boxX][boxY].cantBe(number);
			}
	}
		
	
	
	
	//logicCycles() continuously cycles through the different logic algorithms until no more changes are being made.
	public void logicCycles()throws Exception
	{
		
		//while(isSolved() == false)
		//{
			int changesMade = 0;
			do
			{
				changesMade = 0;
				changesMade += logic1();
				changesMade += logic2();
				changesMade += logic3();
				changesMade += logic4();
				if(errorFound())
					break;
			}while(changesMade != 0);
	
		//}			
		
	}
	
	
	///TODO: logic1
	/*This method searches each row of the puzzle and looks for cells that only have one potential.  If it finds a cell like this, it solves the cell 
	 * for that number. This also tracks the number of cells that it solved as it traversed the board and returns that number.
	 */
	public int logic1()
	{
		int changesMade = 0;
		for(int x=0;x<board.length;x++) {
			for(int y=0;y<board[0].length;y++) {
				if(board[x][y].numberOfPotentials()==1) {
					/*
					boolean[] potential=board[x][y].getPotential();
					for(int i=1;i<potential.length;i++)
						if(potential[i]) {
							changesMade++;
							solve(x,y,i);
						}*/
					
					if(board[x][y].getNumber()==0) {
						changesMade++;
						solve(x,y,board[x][y].getFirstPotential());
					}
					
				}
			}
		}
		
		return changesMade;
					
	}
	
	///TODO: logic2
	/*This method searches each row for a cell that is the only cell that has the potential to be a given number.  If it finds such a cell and it
	 * is not already solved, it solves the cell.  It then does the same thing for the columns.This also tracks the number of cells that 
	 * it solved as it traversed the board and returns that number.
	 */
	
	public int logic2()
	{
		int changesMade = 0;
		int counter,xysPointer;
		//solve for rows
		for(int x=0;x<board.length;x++) {
			
			for(int num=1;num<10;num++) {
				int[] xys=new int[20];
				counter=0;
				xysPointer=0;

				for(int y=0;y<board[0].length;y++) {
					if(board[x][y].canBe(num)) {
						
						counter++;
						if(counter>=2)
							break;
						
						
						xys[xysPointer++]=x;
						xys[xysPointer++]=y;
						//xys[xysPointer++]=num;
					}
				}
				if(counter<2)
					for(int i=0;i<xysPointer;i+=2) {
						if(board[xys[i]][xys[i+1]].getNumber()==0) {
							changesMade++;
							solve(xys[i],xys[i+1],num);
							
						}
					}
						
			}
						
		}
		
		//solve for columns
		
		for(int y=0;y<board.length;y++) {
			
			for(int num=1;num<10;num++) {
				int[] xys=new int[20];
				counter=0;
				xysPointer=0;

				for(int x=0;x<board[0].length;x++) {
					if(board[x][y].canBe(num)) {
						counter++;
						if(counter>=2)
							break;
						xys[xysPointer++]=x;
						xys[xysPointer++]=y;
						//xys[xysPointer++]=num;
					}
				}
				
				if(counter<2)
					for(int i=0;i<xysPointer;i+=2) {
						if(board[xys[i]][xys[i+1]].getNumber()==0) {
							changesMade++;
							solve(xys[i],xys[i+1],num);
						}
						
					}
				
				
			}
						
		}
			
					
		
		
		return changesMade;
	}
	
	///TODO: logic3
	/*This method searches each box for a cell that is the only cell that has the potential to be a given number.  If it finds such a cell and it
	 * is not already solved, it solves the cell. This also tracks the number of cells that it solved as it traversed the board and returns that number.
	 */
	public int logic3()
	{
	
		int changesMade = 0;
		int i,j,counter,xysPointer;
		
		for(int id=1;id<10;id++) {
			i=((id-1)/3)*3;
			j=3*((id-1)%3);
			for(int num=1;num<10;num++) {
				counter=0;
				int[] xys=new int[18];
				xysPointer=0;
				for(int boxX=i;boxX<i+3;boxX++) {
					for(int boxY=j;boxY<j+3;boxY++) {
						if(board[boxX][boxY].canBe(num)) {
							counter++;
							if(counter>=2)
								break;
							xys[xysPointer++]=boxX;
							xys[xysPointer++]=boxY;
						}
					
					}
					
				}
				if(counter<2)
						for(int k=0;k<xysPointer;k+=2) {
							if(board[xys[k]][xys[k+1]].getNumber()==0) {
								changesMade++;
								solve(xys[k],xys[k+1],num);
							}
							
						}
			}
		}
		return changesMade;
	}
	
	
	///TODO: logic4
		/*This method searches each row for the following conditions:
		 * 1. There are two unsolved cells that only have two potential numbers that they can be  
		 * 2. These two cells have the same two potentials (They can't be anything else)
		 * 
		 * Once this occurs, all of the other cells in the row cannot have these two potentials.  Write an algorithm to set these two potentials to be false
		 * for all other cells in the row.
		 * 
		 * Repeat this process for columns and rows.
		 * 
		 * This also tracks the number of cells that it solved as it traversed the board and returns that number.
		 */
	public int logic4()	
	{
		int changesMade = 0;
		
		for(int x=0;x<board.length;x++) {
			HashMap<ArrayList<Integer>,Integer> rowTable=new HashMap<ArrayList<Integer>,Integer>();
			for(int y=0;y<board[0].length;y++) {
				
				if(board[x][y].getNumber()==0&&board[x][y].numberOfPotentials()==2) {
					
					//potentials that are open
					ArrayList<Integer> pts=board[x][y].getAvailablePotentials();
					if(rowTable.containsKey(pts)) {
						for(int e=0;e<board[0].length;e++) {
							if(e!=rowTable.get(pts)&&e!=y)
								for(int p=0;p<pts.size();p++) {
									if(board[x][e].canBe(pts.get(p)))
										changesMade++;
									board[x][e].cantBe(pts.get(p));
								}
						}
						}
					else
						rowTable.put(pts, y);
					
				}
			}
			
		}
		
		for(int y=0;y<board[0].length;y++) {
			HashMap<ArrayList<Integer>,Integer> rowTable=new HashMap<ArrayList<Integer>,Integer>();
			for(int x=0;x<board.length;x++) {
				
				if(board[x][y].getNumber()==0&&board[x][y].numberOfPotentials()==2) {
					
					//potentials that are open
					ArrayList<Integer> pts=board[x][y].getAvailablePotentials();
					if(rowTable.containsKey(pts)) {
						for(int e=0;e<board.length;e++) {
							if(e!=rowTable.get(pts)&&e!=x)
								for(int p=0;p<pts.size();p++) {
									if(board[e][y].canBe(pts.get(p)))
										changesMade++;
									board[e][y].cantBe(pts.get(p));
								}
						}
						}
					else
						rowTable.put(pts, x);
					
				}
			}
			
		}
		
int i,j;
		
		for(int id=1;id<10;id++) {
			i=((id-1)/3)*3;
			j=3*((id-1)%3);
			HashMap<ArrayList<Integer>,ArrayList<Integer>> boxTable=new HashMap<ArrayList<Integer>,ArrayList<Integer>>();
			for(int boxX=i;boxX<i+3;boxX++) {
				for(int boxY=j;boxY<j+3;boxY++) {
					if(board[boxX][boxY].numberOfPotentials()==2&&board[boxX][boxY].getNumber()==0) {
						ArrayList<Integer> pts= board[boxX][boxY].getAvailablePotentials();
						if(boxTable.containsKey(pts)) {
							//loops throught the box and turn off potentials
							for(int cX=i;cX<i+3;cX++) {
								for(int cY=j;cY<j+3;cY++) {
									if(cX!=boxX&&cY!=boxY&&cX!=boxTable.get(pts).get(0)&&cY!=boxTable.get(pts).get(1))
										for(int x=0;x<pts.size();x++) {
											if(board[cX][cY].canBe(pts.get(x)))
												changesMade++;
											board[cX][cY].cantBe(pts.get(x));
											}
								}
							}
						}
						else {
							ArrayList<Integer> xys=new ArrayList<Integer>();
							xys.add(boxX);
							xys.add(boxY);
							boxTable.put(pts, xys);
						}
					}
				}
			}
			}
		
		return changesMade;
	}
	
	public int[] firstUnsolved() {
		int [] xys=new int[2];
		for(int x=0;x<board.length;x++) {
			for(int y=0;y<board[0].length;y++) {
				if(board[x][y].getNumber()==0) {
					xys[0]=x;
					xys[1]=y;
					return xys;
				}
					
			}
		}
		return null;
	}
	Cell[][] boardCopy(Cell[][] board){
		int number,id,i,j,num;
		Cell[][] copyBoard=new Cell[board.length][board[0].length];
		
		for(int x=0;x<board.length;x++) {
			for(int y=0;y<board[0].length;y++) {
				copyBoard[x][y]=new Cell();
				number=board[x][y].getNumber();
				copyBoard[x][y].setBoxID( board[x][y].getBoxID());
				if(number!=0)
					copyBoard[x][y].setNumber(number);
				//copyBoard[x][y].setPotential(board[x][y].getPotential());
				
				for(num=1;num<10;num++) {
					if(!board[x][y].canBe(num))
						copyBoard[x][y].cantBe(num);
				}
				
			}
		}
		return copyBoard;
	}
	
	public void guess() throws Exception{
		//Scanner s=new Scanner(System.in);
		Stack boardState=new Stack();
		int x,y;
		logicCycles();
		/*
		display();
		boardState.add(boardCopy());
		board[0][0].setNumber(0);*/
		
		while(!isSolved()) {
			
			
			int[] firstUnsolved=firstUnsolved();
			//find the first unsolved cell
			if(firstUnsolved!=null) {
				
								
				//save current board
				logicCycles();
				boardState.push(boardCopy(board));
				/*
				display();
				displayPotentials();
				s.next();*/
				
				/*
				for(x=0;x<board.length;x++) {
					for(y=0;y<board[0].length;y++) {
						System.out.print(((Cell[][])(boardState.peek()))[x][y].getNumber()+" ");
						}
					System.out.println();	
				}*/
				
				x=firstUnsolved[0];
				y=firstUnsolved[1];		
				//solve for the first unsolved cell with its first potential
				//if(board[x][y].numberOfPotentials()>0)
				solve(x,y,board[x][y].getFirstPotential());
						
				//do logicCycles again
				logicCycles();
				/*System.out.println("After guess");
				display();
				displayPotentials();
				System.out.println(errorFound());
				s.next();*/
				//if any potential becomes zero
				if(errorFound()) {
				/*
					// turn off the first potential of the unsolved cell.
					if(board[x][y].getFirstPotential()>0) {
						board=(Cell[][])boardState.lastElement();
						board[x][y].cantBe(board[x][y].getFirstPotential());
					}
					else {
						if(!boardState.isEmpty()) {
							boardState.pop();
							if(board[x][y].getFirstPotential()>0) {
								board=(Cell[][])boardState.lastElement();
								board[x][y].cantBe(board[x][y].getFirstPotential());
							}
						}
					}*/
					
					board=boardCopy((Cell[][]) boardState.peek());
					board[x][y].cantBe(board[x][y].getFirstPotential());
				}
			}	
		}
		
	}
	
	
	///TODO: errorFound
	/*This method scans the board to see if any logical errors have been made.  It can detect this by looking for a cell that no longer has the potential to be 
	 * any number.
	 */
	public boolean errorFound()
	{
		for(int x=0;x<board.length;x++)
			for(int y=0;y<board[0].length;y++)
				if(board[x][y].numberOfPotentials()==0)
					return true;
		return false;
	}
	
	
	
	
}
