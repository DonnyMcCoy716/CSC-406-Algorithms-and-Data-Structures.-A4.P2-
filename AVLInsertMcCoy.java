package A4P2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class AVLInsertMcCoy {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.print("Please input the correct amount of files ");
			System.exit(0);
		} else {
			try {
				new AVLInsertMcCoy(args);
			} catch (IOException e) {
				System.out.println("Constrctor Fails");
				e.printStackTrace();
			}
		}

	}

	AVLNodeMcCoy topNode = null;

	public AVLInsertMcCoy(String[] args) throws IOException {
		// create Scanner
		Scanner Sc = new Scanner(new File(args[0]));
		// create printwriter to output
		PrintWriter w = new PrintWriter(new File(args[1]));

		LinkedList<Integer> input = new LinkedList<Integer>();
		while (Sc.hasNextLine()) {
			int node = Sc.nextInt();
			input.add(node);
		}

		for (int i = 0; i < input.size(); i++) {

			topNode = insert((int) input.get(i), topNode);
			w.println(printTree(topNode) + "\n");
		}

		Sc.close();// close scanner
		w.close();// close printwriter
	}

	private boolean h;

	public AVLNodeMcCoy insert(int k, AVLNodeMcCoy p) {
		if (p == null) {
			p = new AVLNodeMcCoy();
			p.value = k;
			p.leftPoint = p.rightPoint = null;
			p.balance = 0;
			h = true;
		} else if (k < p.value) {
			p.leftPoint = insert(k, p.leftPoint);
			if (h) {
				// tree grew to left
				switch (p.balance) {
				case 0:
					p.balance = -1;
					break;
				case +1:
					p.balance = 0;
					h = false;
					break;
				case -1:
					AVLNodeMcCoy p1 = p.leftPoint;
					if (p1.balance == -1)
						p = LL(p);
					else
						p = LR(p);
				}
			}
		} else {
			p.rightPoint = insert(k, p.rightPoint);
			if (h) {
				// tree grew to right
				switch (p.balance) {
				case 0:
					p.balance = +1;
					break;
				case -1:
					p.balance = 0;
					h = false;
					break;
				case +1:
					AVLNodeMcCoy p1 = p.rightPoint;
					if (p1.balance == +1)
						p = RR(p);
					else
						p = RL(p);
				}
			}
		}
		return p;
	}

	public AVLNodeMcCoy RR(AVLNodeMcCoy p) {
		AVLNodeMcCoy p1 = p.rightPoint;
		p.rightPoint = p1.leftPoint;
		p.balance = 0;
		p1.leftPoint = p;
		p1.balance = 0;
		p = p1;
		h = false;
		return p;
	}

	public AVLNodeMcCoy LL(AVLNodeMcCoy p) {
		AVLNodeMcCoy p1 = p.leftPoint;
		p.leftPoint = p1.rightPoint;
		p.balance = 0;
		p1.rightPoint = p;
		p1.balance = 0;
		p = p1;
		p.balance = 0;
		h = false;
		return p;
	}

	public AVLNodeMcCoy RL(AVLNodeMcCoy p) {
		AVLNodeMcCoy p1 = p.rightPoint;
		AVLNodeMcCoy p2 = p1.leftPoint;
		p1.leftPoint = p2.rightPoint;
		p.rightPoint = p2.leftPoint;
		p2.leftPoint = p;
		p2.rightPoint = p1;
		if (p2.balance == 0) {
			p.balance = 0;
			p1.balance = 0;
		}
		if (p2.balance == -1) {
			p.balance = 0;
			p1.balance = +1;
		}
		if (p2.balance == +1) {
			p.balance = -1;
			p1.balance = 0;
		}
		p = p2;
		p.balance = 0;
		h = false;
		return p;
	}

	public AVLNodeMcCoy LR(AVLNodeMcCoy p) {
		AVLNodeMcCoy p1 = p.leftPoint;
		AVLNodeMcCoy p2 = p1.rightPoint;
		p1.rightPoint = p2.leftPoint;
		p.leftPoint = p2.rightPoint;
		p2.rightPoint = p;
		p2.leftPoint = p1;
		if (p2.balance == 0) {
			p.balance = 0;
			p1.balance = 0;
		}
		if (p2.balance == -1) {
			p.balance = +1;
			p1.balance = 0;
		}
		if (p2.balance == +1) {
			p.balance = 0;
			p1.balance = -1;
		}
		p = p2;
		p.balance = 0;
		h = false;
		return p;
	}

	public String printTree(AVLNodeMcCoy p) {
		String output = "";
		if (p != null)
			output = output + p.value + " (" + printTree(p.leftPoint) + ")" + " (" + printTree(p.rightPoint) + ") ";
		return output;
	}

}