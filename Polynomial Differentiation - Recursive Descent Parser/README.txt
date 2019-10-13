Assignment-6

INPUT: A completely parenthesized polynomial in string format

CONSTRAINTS: 
	The polynomial is allowed to have the following functions (operators):
	• Addition f + g
	• Subtraction f – g
	• Multiplication f*g
	• Division f/g
	• Exponentiation f^g

OUTPUT: A parenthesized polynomial in string format which is the differentiation of the given polynomial

IMPLEMENTATION:
	• PARSING:
		➢ Using the parenthesized polynomial the given polynomial was parsed into a PROPER BINARY TREE whose in which the leaf nodes represented the literals(variables and constants) of the polynomial and all the non-leaf nodes represented one of the above mentioned operators.
		➢ For each non-leaf node of the binary tree, representing an operator, its left and right children represent its left and right operands respectively.
		➢ Since the polynomial was completely parenthesized parsing was done by creating a node for the topmost operator and setting its left child as the parsed binary tree of the left operand in the operator and the right child as the parsed binary tree of the right operand of the operator. Therefore RECURSION was used to parse the whole polynomial.
	• DIFFERENTIATION:
		➢ Differentiation for each type of operation was taken care of separately.
		➢ RECURSION was invoked to further differentiate the operands of the operations using the CHAIN RULE.
	• PRINTING THE BINARY TREE (DIFFERENTIATED POLYNOMIAL):
		➢ Printing was also done recursively using the INORDER TRAVERSAL of the Binary Tree.
		➢ The left operator was printed first then the operator and then right operand.