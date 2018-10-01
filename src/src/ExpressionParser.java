package src;

import java.util.Scanner;

public class ExpressionParser
{
    private String expression;
    private int pos = -1;
    private char ch;

    public ExpressionParser(String expression)
    {
        this.expression = expression;
    }
    
    private void nextChar(){
        ch = (char) (( ++pos < expression.length() ) ? expression.charAt(pos) : -1);
    }
    
    private boolean eat(char c)
    {
        while(ch==' ')
            nextChar();
        
        if( ch==c )
        {
            nextChar();
            return true;
        }
        
        return false;
    }
    
    public double parse()
    {
        nextChar();
        double x = parseExpression();
        return x;
    }
    
    private double parseExpression()
    {
        double x = parseTearm();
        
        while( true )
        {
            if( eat('+') )
                x += parseTearm();
            else if( eat('-') )
                x -= parseTearm();
            else return x;
        }
    }
    
    private double parseTearm()
    {
        double x = parseFactor();
        
        while( true )
        {
            if( eat('*') )
                x *= parseFactor();
            else if( eat('/') )
                x /= parseFactor();
            else
                return x;
        }
    }
    
    // Izdvajanje broja, stepena ili zagrada
    private double parseFactor()
    {
        eat(' ');
        int start = pos;
        
        // Broj
        if( ch>='0' && ch<='9' )
        {
            nextChar();
            while( (ch>='0' && ch<='9') || ch=='.' )
                nextChar();
            
            double x = Double.valueOf( expression.substring(start, pos) );
            // Stepen
            if( eat('^') )
                x = Math.pow(x,  parseFactor() );
            
            return x;
        }
        
        // Zagrade
        if( eat('(') )
        {
            double x = parseExpression();
            eat(')');
            return x;
        }
        
        return 0;
    }
            
    public static void main(String[] args)
    {
        ExpressionParser parser = new ExpressionParser( new Scanner(System.in).nextLine() );
        System.out.println(  parser.parse());
    }
    
}
