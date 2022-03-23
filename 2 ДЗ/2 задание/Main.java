import java.util.HashMap;
import java.util.Map;

enum SuitCard
{
    DIAMONDS,
    CLUBS,
    HEARTS,
    SPADES
};

class CardFirst
{
    public Integer rank;
    public SuitCard suit;
    
    CardFirst(){}
}

class CardSecond
{
    private Integer rank = 6;
    private SuitCard suit = SuitCard.DIAMONDS;
    
    CardSecond( Integer rank, SuitCard suit )
    {   
        // Рассматриваем стандартную колоду карт на 36 карт.
        // Начальный элемент - 6, конечный - туз (14).
        if( rank > 5 && rank < 15 )
        {
            this.rank = rank;
            this.suit = suit;
        }
        else
        {
            System.out.println( "Некоректное значение ранга карты. Значение ранга карты должно быть от 6 до 14" );
        }
        
    }
          
    SuitCard getSuit()
    {
        return this.suit;
    }
    
    Integer getRank()
    {
        return this.rank;
    }
}

class CardThird
{
    private Integer rank = 6;
    private SuitCard suit = SuitCard.DIAMONDS;
    private static Map< SuitCard, Integer > maxRankInSuit = new HashMap<>();
    static {
        maxRankInSuit.put( SuitCard.DIAMONDS, 6 );
        maxRankInSuit.put( SuitCard.CLUBS, 6 );
        maxRankInSuit.put( SuitCard.HEARTS, 6 );
        maxRankInSuit.put( SuitCard.SPADES, 6 );
    }
    
    CardThird( Integer rank, SuitCard suit )
    {   
        // Рассматриваем стандартную колоду карт на 36 карт.
        // Начальный элемент - 6, конечный - туз (14).
        if( isCorrectRank( rank ) )
        {
            this.rank = rank;
            this.suit = suit;
            if( isGreaterThanMaximum( rank, suit ) )
            {
                CardThird.maxRankInSuit.replace( suit, rank );
            }
        }
        else
        {
            System.out.println( "Некоректное значение ранга карты. Значение ранга карты должно быть от 6 до 14" );
        }  
    }
    
    CardThird( SuitCard suit )
    {
        this( CardThird.maxRankInSuit.get(suit) + 1, suit );
    }
    
    public SuitCard getSuit()
    {
        return this.suit;
    }
    
    public Integer getRank()
    {
        return this.rank;
    }

    private boolean isCorrectRank( Integer rank )
    {
        return rank > 5 && rank < 15;
    }

    private boolean isGreaterThanMaximum( Integer rank, SuitCard suit )
    {
        return CardThird.maxRankInSuit.get( suit ) < rank;
    }
}

public class Main
{
	public static void main(String[] args) {
		CardFirst first = new CardFirst();
        first.rank = 6;
        first.suit = SuitCard.CLUBS;

        CardSecond second = new CardSecond( 7, SuitCard.HEARTS );
        System.out.println( "Card second. Rank:" + second.getRank() + " suit:" + second.getSuit() );
        CardSecond second_uncorrect = new CardSecond(5, SuitCard.CLUBS);

        CardThird third = new CardThird( 7, SuitCard.HEARTS );
        System.out.println( "Card third. Rank:" + third.getRank() + " suit:" + third.getSuit() );

        third = new CardThird( SuitCard.HEARTS );
        System.out.println( "Card third. Rank:" + third.getRank() + " suit:" + third.getSuit() );

	}
}
