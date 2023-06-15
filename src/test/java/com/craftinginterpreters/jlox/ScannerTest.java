package com.craftinginterpreters.jlox;

import static org.junit.Assert.*;

import java.util.List;
import static com.craftinginterpreters.jlox.TokenType.*;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class ScannerTest
{
    @Test
    public void emptySourceShouldGenerateOnlyEOF() {
        String source = "   \t \n   \n \t";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertEquals(1, tokens.size());
        assertEquals(EOF, tokens.get(0).type);
    }

    @Test
    public void shouldIgnoreComments() {
        String source = "// hello world !!!";
        Scanner scanner = new Scanner(source);

        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(EOF, tokens.get(0).type);
    }

    @Test
    public void shouldRecognizeAllSymbols()
    {
        String source = "! + - * = == < > { } ( ) ;  != <= >=";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        assertEquals(17, tokens.size()); // 16 lexemas + EOF
        assertEquals(BANG, tokens.get(0).type);
        assertEquals(PLUS, tokens.get(1).type);
        assertEquals(MINUS, tokens.get(2).type);
        assertEquals(STAR, tokens.get(3).type);
        assertEquals(EQUAL, tokens.get(4).type);
        assertEquals(EQUAL_EQUAL, tokens.get(5).type);
        assertEquals(LESS, tokens.get(6).type);
        assertEquals(GREATER, tokens.get(7).type);
        assertEquals(LEFT_BRACE, tokens.get(8).type);
        assertEquals(RIGHT_BRACE, tokens.get(9).type);
        assertEquals(LEFT_PAREN, tokens.get(10).type);
        assertEquals(RIGHT_PAREN, tokens.get(11).type);
        assertEquals(SEMICOLON, tokens.get(12).type);
        assertEquals(BANG_EQUAL, tokens.get(13).type);
        assertEquals(LESS_EQUAL, tokens.get(14).type);
        assertEquals(GREATER_EQUAL, tokens.get(15).type);
    }

    @Test
    public void shouldRecognizeIdentifiersAndKeywords() {
        String source = "    var variable while\n whileVar\t for foreach";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertEquals(7, tokens.size());
        assertEquals("var", tokens.get(0).lexeme);
        assertEquals(VAR, tokens.get(0).type);
        assertEquals("variable", tokens.get(1).lexeme);
        assertEquals(IDENTIFIER, tokens.get(1).type);
        assertEquals("while", tokens.get(2).lexeme);
        assertEquals(WHILE, tokens.get(2).type);
        assertEquals("whileVar", tokens.get(3).lexeme);
        assertEquals(IDENTIFIER, tokens.get(3).type);
        assertEquals("for", tokens.get(4).lexeme);
        assertEquals(FOR, tokens.get(4).type);
        assertEquals("foreach", tokens.get(5).lexeme);
        assertEquals(IDENTIFIER, tokens.get(5).type);
    }

    @Test
    public void shouldRecognizeStringLiterals() {
        String source = " \"hello\" ";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertEquals(2, tokens.size());
        assertEquals("\"hello\"", tokens.get(0).lexeme);
        assertEquals("hello", tokens.get(0).literal);
        assertEquals(STRING, tokens.get(0).type);
    }

    @Test
    public void shouldNotRecognizeIncompleteStringLiterals() {
        String source = "\"wrong";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        
        assertEquals(1, tokens.size());
        assertEquals(EOF, tokens.get(0).type);
    }

    @Test
    public void shouldRecognizeNumberLiterals() {
        String source = "1 12321 123.234 2321.2";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        assertEquals(5, tokens.size());
        for (int i = 0; i < 4; i++) {
            assertEquals(NUMBER, tokens.get(i).type);
        }
        assertEquals(1.0, tokens.get(0).literal);
        assertEquals(12321.0, tokens.get(1).literal);
        assertEquals(123.234, tokens.get(2).literal);
        assertEquals(2321.2, tokens.get(3).literal);
    }
}
