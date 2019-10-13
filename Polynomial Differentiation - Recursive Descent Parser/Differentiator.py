class expr:
    '''expr is th class of expressions'''

    def __init__(self, S, Nd=None):
        '''Constructor of expr'''
        if (Nd):
            self.expr = Nd
        else:
            (e, n) = self.parse(S)
            self.expr = e

    class Node:
        '''Node is the class of Nodes used to implement the ADT:binary tree'''

        def __init__(self, d):
            '''Constructor of Node'''
            self.left = None
            self.right = None
            self.data = d

        def toString(self):
            '''Used to convert a given binary tree to a string expression i.e. into a readable string expression'''
            if self.data == '@':      # @ is used to represent ln(log to base e) symbolically in the tree and is printed as ln using this condition
                right = self.right.toString()
                return "(ln[" + right + "])"
            # Removing 0.0 : 0.0 - right  as we do not have unary operator '-'
            if self.data == '-' and self.left.toString() == '0.0':
                # we simply do not print 0.0 when such an expression is encountered
                right = self.right.toString()
                return "(" + "- " + right + ")"
            if (self.left and self.right):
                left = self.left.toString()
                right = self.right.toString()
                opr = self.data
                return "(" + left + " " + opr + " " + right + ")"
            else:
                return self.data

    def prettyprint(self):
        '''Used to print a binary treee representation of an expression as a readable string expression'''
        s = self.expr.toString()
        print(s)

    def parse(self, S):
        '''Used to parse a given string expression and convert it into a binary tree in which a Node represents either a binary operator or
           a vaariable or a constant
           (i)  if the node is a binary operator then it has its two operands(which may be expressions) as the two sub-nodes of the binary tree
           (ii) if it is a constant or a variable then its subnodes are None'''
        l = len(S)
        if (S[0] == "("):
            (left, n) = self.parse(S[1:l - 2])
            opr = S[n + 1]
            (right, m) = self.parse(S[n + 2:l - 1])
            expr = self.Node(opr)
            expr.left = left
            expr.right = right
            return (expr, n + m + 3)
        elif S[0].isdigit():
            i = 0
            # INV:S[i(Initial=0):i(Current State)] contains only numbers and '.'s (decimal points)
            while ((i < l) and (S[i].isdigit() or (S[i] == "."))):
                i = i + 1
            # Assert:S[0:i] contains only numbers and '.'s (decimal points)
            num = S[0:i]
            expr = self.Node(num)
            return (expr, i)
        elif S[0].isalpha():
            i = 0
            while ((i < l) and S[i].isalpha()):
                i = i + 1
            var = S[0:i]
            expr = self.Node(var)
            return (expr, i)
        else:
            return Exception("Invalid input")

    def constant(self):
        '''Checks whether a node is a constant or not'''
        if self.expr.data[0].isdigit():
            return True
        else:
            return False

    def variable(self):
        '''Checks whether a node is a variable or not'''
        if self.expr.data[0].isalpha():
            return True
        else:
            return False

    def samevariable(self, x):
        '''Checks whether a node and the variable with respect to which we are differentiating are same'''
        if (self.expr.data == x):
            return True
        else:
            return False

    def sum(self):
        '''Checks whether a node is an addition operation or not'''
        if (self.expr.data == '+'):
            return True
        else:
            return False

    def addend(self):
        '''Returns the addend if the node is an addition operator'''
        if self.sum():
            left = self.expr.left
            return expr("", left)
        else:
            raise Exception('Not a sum operation')

    def augend(self):
        '''Returns the augend if the node is an addition operator '''
        if self.sum():
            right = self.expr.right
            return expr("", right)
        else:
            return Exception('Not a sum operation')

    def makesum(self, e1, e2):
        '''returns the expression for the sum of the 2 expressions e1 and e2'''
        if e1.expr.toString() == '0.0':     # Removing 0.0 : 0.0 + e2 = e2
            return e2
        elif e2.expr.toString() == '0.0':   # Removing 0.0 : e1 + 0.0 = e1
            return e1
        else:
            e = self.Node("+")
            e.left = e1.expr
            e.right = e2.expr
            return expr("", e)

    def sub(self):
        '''Checks whether a node is a suntraction operation or not'''
        if (self.expr.data == '-'):
            return True
        else:
            return False

    def subtrahead(self):
        '''Returns the subtrahead if the node is a subtraction operation'''
        if self.sub():
            left = self.expr.left
            return expr("", left)
        else:
            raise Exception('Not a subtraction expression')

    def minuend(self):
        '''Returns the muniend if the node is a subtraction operation'''
        if self.sub():
            right = self.expr.right
            return expr("", right)
        else:
            raise Exception('Not a subtraction operation')

    def makesub(self, e1, e2):
        '''Returns the expression for e1 - e2'''
        if e2.expr.toString() == '0.0':       # Removing 0.0 : e1 - 0.0 = e1
            # Removing 0.0 : 0.0 - e2 = - e2 (No unary '-'): Case handled in the method toString()
            return e1
        e = self.Node("-")
        e.left = e1.expr
        e.right = e2.expr
        return expr("", e)

    def prod(self):
        '''Checks whether a node is a multiplication operation or not'''
        if (self.expr.data == '*'):
            return True
        else:
            return False

    def multiplicand(self):
        '''Returns the multiplicand if the node is a multiplication operation'''
        if self.prod():
            left = self.expr.left
            return expr("", left)
        else:
            raise Exception('Not a muktiplication operation')

    def multiplier(self):
        '''Returns the multiplier if the node is a multiplication operation'''
        if self.prod():
            right = self.expr.right
            return expr("", right)
        else:
            raise Exception('Not a muktiplication operation')

    def makeprod(self, e1, e2):
        '''returns the expression for e1 * e2'''
        if e1.expr.toString() == '1.0':       # removing 1.0 : 1.0 * e2 = e2
            return e2
        elif e2.expr.toString() == '1.0':     # removing 1.0 : e1 * 1.0 = e1
            return e1
        if e1.expr.toString() == '0.0':       # removing 0.0 : 0.0 * e2 = e2
            return expr('0.0')
        elif e2.expr.toString() == '0.0':     # removing 0.0 : e1 * 0.0 = e1
            return expr('0.0')
        else:
            e = self.Node("*")
            e.left = e1.expr
            e.right = e2.expr
            return expr("", e)

    def div(self):
        '''Checks whether a node is a division operation or not'''
        if (self.expr.data == '/'):
            return True
        else:
            return False

    def divident(self):
        '''Returns the divident if the node is an division operation'''
        if self.div():
            left = self.expr.left
            return expr("", left)
        else:
            raise Exception('Not a division operation')

    def divisor(self):
        '''Returns the divisor if the node is an division operation'''
        if self.div():
            right = self.expr.right
            return expr("", right)
        else:
            raise Exception('Not a division operation')

    def makediv(self, e1, e2):
        '''Returns the expression for e1/e2'''
        if e1.expr.toString() == '0.0':         # removing 0.0 : 0.0 / e2 = 0.0
            return expr('', e1.expr)
        elif e2.expr.toString() == '0.0':       # raising exception for expressions like e1 / 0.0
            raise Exception('ZeroDivisionError')
        else:
            e = self.Node("/")
            e.left = e1.expr
            e.right = e2.expr
            return expr("", e)

    def exp(self):
        '''Checks whether a node is an exponentiation operation or not'''
        if (self.expr.data == '^'):
            return True
        else:
            return False

    def base(self):
        '''Returns the base if the node is an exponentiation operation'''
        if self.exp():
            left = self.expr.left
            return expr("", left)
        else:
            raise Exception('Not exponentiation')

    def exponent(self):
        '''Returns the exponent if the node is an exponentiation operation'''
        if self.exp():
            right = self.expr.right
            return expr("", right)
        else:
            raise Exception('Not exponentiation')

    def makeexp(self, e1, e2):
        '''Returns the expression for e1^e2'''
        if e1.expr.toString() == '0.0':        # removing 0.0 : 0.0 ^ e2 = 0.0
            return expr('0.0')
        elif e1.expr.toString() == '1.0':      # removing 1.0 : 1.0 ^ e2 = 1.0
            return expr('1.0')
        elif e2.expr.toString() == '0.0':      # removing 0.0 : e1 ^ 0.0 = 1.0
            return expr('1.0')
        elif e2.expr.toString() == '1.0':      # removing 1.0 : e1 ^ 1.0 = e1
            return e1
        else:
            e = self.Node("^")
            e.left = e1.expr
            e.right = e2.expr
            return expr("", e)

    def makelog(self, e2):
        '''Returns the expression for e @ e2 which represents ln(e2) '''
        e1 = expr('e')
        if e2.expr.toString() == '1.0':        # removing 0.0 : e @ 1.0 = ln 1.0 = 0.0
            return expr('0.0')
        elif e2.expr.toString() == '0.0':      # raising exception for the expression like e @ 0.0 = ln 0.0
            raise Exception('ZeroLogError')
        else:
            e = self.Node("@")
            e.left = e1.expr
            e.right = e2.expr
            return expr("", e)

    def deriv(self, x):
        '''Returns the expression of symbolic differentiation of the given expression'''
        if self.constant():
            return expr("0.0")
        if self.variable():
            if self.samevariable(x):
                return expr("1.0")
            else:
                return expr("0.0")
        elif self.sum():
            e1 = self.addend()
            e2 = self.augend()
            return self.makesum(e1.deriv(x), e2.deriv(x))
        elif self.sub():
            e1 = self.subtrahead()
            e2 = self.minuend()
            return self.makesub(e1.deriv(x), e2.deriv(x))
        elif self.prod():
            e1 = self.multiplicand()
            e2 = self.multiplier()
            return self.makesum(self.makeprod(e1.deriv(x), e2), self.makeprod(e1, e2.deriv(x)))
        elif self.div():
            e1 = self.divident()
            e2 = self.divisor()
            return self.makediv(self.makesub(self.makeprod(e1.deriv(x), e2), self.makeprod(e1, e2.deriv(x))), self.makeprod(e2, e2))
        elif self.exp():
            e1 = self.base()
            e2 = self.exponent()
            if e2.constant():
                diffe = expr(str(float(e2.expr.toString()) - 1.0))
                return self.makesum(self.makeprod(self.makeprod(e2, self.makeexp(e1, diffe)), e1.deriv(x)), self.makeprod(self, self.makeprod(self.makelog(e1), e2.deriv(x))))
            else:
                return self.makesum(self.makeprod(self.makeprod(e2, self.makeexp(e1, self.makesub(e2, expr('1.0')))), e1.deriv(x)), self.makeprod(self, self.makeprod(self.makelog(e1), e2.deriv(x))))
        else:
            raise Exception("DontKnowWhatToDo!")


a = input("Enter an expression:")
e = expr(a)
e.prettyprint()
x = input('Enter the variable with respect to which derivative is to be taken:')
f = e.deriv(x)
print('Derviative of the above expression wrt ' + x + ' : ', end='')
f.prettyprint()

# Code for Assignment6 by : Satwik Banchhor 2018CS10385
# Group:29
# The basic code is made using the example given in class for symbolic differentiation of addition by Prof. Suban
# Discussions done with : 1.Sanyam Ahuja 2018CS10280 Group:23 and 2.Shreyans Nagori 2018CS10390 Group:26
