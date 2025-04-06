import random
import itertools

def generate_valid_number(user_sequence=None):
    operators = ["+", "-", "*", "/"]
    while True:
        if user_sequence:
            num_str = user_sequence
        else:
            num_str = str(random.randint(100000, 999999))

        valid_expressions = []

        combinations = list(itertools.product(operators, repeat=len(num_str) - 1))

        for ops in combinations:
            expr = "".join(
                [
                    num_str[i] + (ops[i] if i < len(ops) else "")
                    for i in range(len(num_str))
                ]
            )
            try:
                if evaluate(expr) == 100.0:
                    valid_expressions.append(expr)
            except (ZeroDivisionError, ValueError, IndexError):
                # Handle division by zero, invalid input or index errors
                pass

        if valid_expressions:
            return num_str, random.choice(valid_expressions)
        if user_sequence:
            return num_str, "No valid expression found" # if no valid expression found for user input, return this.

def evaluate(expression):
    class Evaluator:
        def __init__(self, expression):
            self.expression = expression
            self.pos = -1
            self.ch = 0
            self.next_char()

        def next_char(self):
            self.pos += 1
            self.ch = ord(self.expression[self.pos]) if self.pos < len(self.expression) else -1

        def eat(self, char_to_eat):
            while self.ch == ord(' '):
                self.next_char()
            if self.ch == char_to_eat:
                self.next_char()
                return True
            return False

        def parse(self):
            x = self.parse_expression()
            if self.pos < len(self.expression):
                raise ValueError("Unexpected: " + chr(self.ch))
            return x

        def parse_expression(self):
            x = self.parse_term()
            while True:
                if self.eat(ord('+')):
                    x += self.parse_term()
                elif self.eat(ord('-')):
                    x -= self.parse_term()
                else:
                    return x

        def parse_term(self):
            x = self.parse_factor()
            while True:
                if self.eat(ord('*')):
                    x *= self.parse_factor()
                elif self.eat(ord('/')):
                    x /= self.parse_factor()
                else:
                    return x

        def parse_factor(self):
            if self.eat(ord('+')):
                return self.parse_factor()
            if self.eat(ord('-')):
                return -self.parse_factor()

            start_pos = self.pos
            if self.eat(ord('(')):
                x = self.parse_expression()
                self.eat(ord(')'))
            elif ord('0') <= self.ch <= ord('9'):
                while ord('0') <= self.ch <= ord('9'):
                    self.next_char()
                x = float(self.expression[start_pos:self.pos])
            else:
                raise ValueError("Unexpected: " + chr(self.ch))
            return x

    return Evaluator(expression).parse()

def is_valid_expression(expression, original_number):
    user_digits = "".join(filter(str.isdigit, expression))
    return user_digits == original_number

# Example usage:
user_input = input("Enter a 6-digit number (or press Enter for a random number): ")
if user_input:
    if len(user_input) != 6 or not user_input.isdigit():
        print("Invalid input. Please enter a 6-digit number.")
    else:
        num_str, valid_expr = generate_valid_number(user_input)
        print(f"Number: {num_str}, Expression: {valid_expr}")

else:
    num_str, valid_expr = generate_valid_number()
    print(f"Number: {num_str}, Expression: {valid_expr}")