enum SuitCard
{
    CLUBS,
    DIAMONDS,
    SPADES,
    HEARTS
};

enum Rank
{
    JACK,
    QUEEN,
    KING,
    ACE,
    JOKER
};

class Card 
{
    private Integer rank = 6;
    private SuitCard suit = SuitCard.DIAMONDS;
    
    Card( Integer rank, SuitCard suit )
    {   
        if( rank > 1 )
        {
            this.rank = rank;
            this.suit = suit;  
        } 
        else
        {
            System.out.println( "Некоректное значение ранга карты. Значение ранга карты должно быть > 1" );
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

    private String getStringRank( Integer rank )
    {
        switch( rank )
        {
            case 11 : return Rank.JACK.toString();
            case 12 : return Rank.QUEEN.toString();
            case 13 : return Rank.KING.toString();
            case 14 : return Rank.ACE.toString();
            case 15 : return Rank.JOKER.toString();
            default : return rank.toString();
        }
    } 

    @Override
    public String toString() {
        return "Rank:" + getStringRank( this.rank ) + " suit:" + this.suit;
    }

    @Override
    public int hashCode()
    {
        int result = suit == null ? 0 : suit.hashCode();
        result = 31 * result + rank;
        return result;
    }

    @Override
    public boolean equals( Object rhs ) 
    {
        if ( rhs == this ) return true;
        if ( !( rhs instanceof Card ) ) 
        {
            return false;
        }
        Card card = (Card) rhs;
        return this.rank == card.rank &&
            this.suit == card.suit;
    }

    public boolean isBelongsStandartDeck()
    {
        return this.rank > 0 && this.rank < 15;
    }

    /**
     * Проверяет, что текушая карта больше по значению, чем переданная карта.
     * Их масти должны быть одинаковы.
     */
    public boolean isMoreRank( Card card )
    {
        return this.suit == card.suit && this.rank > card.rank;
    }

    /**
     * Проверяет, что карта lhs больше по значению и масти, чем карта rhs.
     * Важность мастей clubs < diamonds < spades < hearts.
     */
    static public int isMore( Card lhs, Card rhs )
    {
        if( lhs.suit.ordinal() > rhs.suit.ordinal() )
        {
            return 1;
        }
        else if( lhs.suit.ordinal() == rhs.suit.ordinal() )
        {
            if( lhs.rank > rhs.rank )
            {
                return 1;
            }
            else if( lhs.rank == rhs.rank )
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return -1;
        }
    }

    /**
     * Проверяет, что текушая карта больше по значению и масти, чем переданная карта.
     * Важность мастей clubs < diamonds < spades < hearts.
     */
    public int isMore( Card rhs )
    {
        return Card.isMore(this, rhs);
    }
}

public class Main
{
	public static void main(String[] args) {

        Card uncorrect_card = new Card( -1, SuitCard.CLUBS );
        assert uncorrect_card.getRank() != -1;

        Card correct_card = new Card( 2, SuitCard.CLUBS );
        assert correct_card.equals( new Card( 2, SuitCard.CLUBS ) );
        assert !correct_card.equals( new Card( 3, SuitCard.CLUBS ) );

        assert correct_card.isBelongsStandartDeck();
        assert !new Card( 16, SuitCard.CLUBS ).isBelongsStandartDeck();

        assert !correct_card.isMoreRank(new Card( 2, SuitCard.CLUBS ));
        assert !correct_card.isMoreRank(new Card( 1, SuitCard.CLUBS ));
        assert !correct_card.isMoreRank(new Card( 3, SuitCard.DIAMONDS ));
        assert !new Card( 15, SuitCard.CLUBS ).isMoreRank(new Card( 3, SuitCard.DIAMONDS ));
        assert new Card( 15, SuitCard.CLUBS ).isMoreRank(new Card( 3, SuitCard.CLUBS ));

        assert Card.isMore( new Card( 15, SuitCard.CLUBS ), new Card( 3, SuitCard.CLUBS ) ) == 1;
        assert Card.isMore( new Card( 15, SuitCard.CLUBS ), new Card( 15, SuitCard.CLUBS ) ) == 0;
        assert Card.isMore( new Card( 15, SuitCard.CLUBS ), new Card( 15, SuitCard.DIAMONDS ) ) == -1;

	}
}
