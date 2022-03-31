enum class SuitCard {
    CLUBS, DIAMONDS, SPADES, HEARTS
}

enum class Rank {
    JACK, QUEEN, KING, ACE, JOKER
}

class Card(rank: Int, suit: SuitCard) {
    var rank = 6
    var suit: SuitCard? = SuitCard.DIAMONDS

    init {
        if (rank > 1) {
            this.rank = rank
            this.suit = suit
        } else {
            println("Некоректное значение ранга карты. Значение ранга карты должно быть > 1")
        }
    }

    private fun getStringRank(rank: Int): String {
        return when (rank) {
            11 -> Rank.JACK.toString()
            12 -> Rank.QUEEN.toString()
            13 -> Rank.KING.toString()
            14 -> Rank.ACE.toString()
            15 -> Rank.JOKER.toString()
            else -> rank.toString()
        }
    }

    override fun toString(): String {
        return "Rank:" + getStringRank(rank) + " suit:" + suit
    }

    override fun hashCode(): Int {
        var result = suit.hashCode()
        result = 31 * result + rank
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Card) {
            return false
        }

        return rank === other.rank &&
                suit == other.suit
    }

    val isBelongsStandartDeck: Boolean
        get() = rank > 0 && rank < 15

    /**
     * Проверяет, что текушая карта больше по значению, чем переданная карта.
     * Их масти должны быть одинаковы.
     */
    fun isMoreRank(card: Card): Boolean {
        return suit == card.suit && rank > card.rank
    }

    /**
     * Проверяет, что текушая карта больше по значению и масти, чем переданная карта.
     * Важность мастей clubs < diamonds < spades < hearts.
     */
    fun isMore(rhs: Card): Int {
        return isMore(this, rhs)
    }

    companion object {
        /**
         * Проверяет, что карта lhs больше по значению и масти, чем карта rhs.
         * Важность мастей clubs < diamonds < spades < hearts.
         */
        fun isMore(lhs: Card, rhs: Card): Int {
            return if (lhs.suit!!.ordinal > rhs.suit!!.ordinal) {
                1
            } else if (lhs.suit!!.ordinal == rhs.suit!!.ordinal) {
                if (lhs.rank > rhs.rank) {
                    1
                } else if (lhs.rank === rhs.rank) {
                    0
                } else {
                    -1
                }
            } else {
                -1
            }
        }
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val uncorrect_card = Card(-1, SuitCard.CLUBS)
        assert(uncorrect_card.rank != -1)
        val correct_card = Card(2, SuitCard.CLUBS)
        assert(correct_card == Card(2, SuitCard.CLUBS))
        assert(correct_card != Card(3, SuitCard.CLUBS))
        assert(correct_card.isBelongsStandartDeck)
        assert(!Card(16, SuitCard.CLUBS).isBelongsStandartDeck)
        assert(!correct_card.isMoreRank(Card(2, SuitCard.CLUBS)))
        assert(!correct_card.isMoreRank(Card(1, SuitCard.CLUBS)))
        assert(!correct_card.isMoreRank(Card(3, SuitCard.DIAMONDS)))
        assert(!Card(15, SuitCard.CLUBS).isMoreRank(Card(3, SuitCard.DIAMONDS)))
        assert(Card(15, SuitCard.CLUBS).isMoreRank(Card(3, SuitCard.CLUBS)))
        assert(Card.isMore(Card(15, SuitCard.CLUBS), Card(3, SuitCard.CLUBS)) == 1)
        assert(Card.isMore(Card(15, SuitCard.CLUBS), Card(15, SuitCard.CLUBS)) == 0)
        assert(Card.isMore(Card(15, SuitCard.CLUBS), Card(15, SuitCard.DIAMONDS)) == -1)
    }
}