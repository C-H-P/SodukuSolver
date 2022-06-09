package codes;

import java.util.ArrayList;

public class SudokuSolver {

	public static void main(String[] args)throws Exception {
		
		Board puzzle = new Board();
		puzzle.loadPuzzle("hard");
		puzzle.display();
		puzzle.guess();
		puzzle.display();
		//puzzle.logicCycles();
		//puzzle.display();
		/*
		for(int count=0;count<100;count++) {
			puzzle.logic1();
			puzzle.logic2();
			puzzle.logic3();
			puzzle.logic4();
		}
		System.out.println(puzzle.isSolved());*/
		//puzzle.display();
		
		//System.out.println(puzzle.errorFound());
		//System.out.println(puzzle.isSolved());
		

	}

}
