package Customer;

public class BonusCard {
    private int points;
    private float totalSpend;

    /**
        Constructor
     */
    public BonusCard(int points, float totalSpend) {
        this.points = points;
        this.totalSpend = totalSpend;
    }

    /**
        Updates bonus card based on the total spend by the customers
     */
    public void updateBonusCard(float orderSpend) {
        float oldTotalSpend = totalSpend;
        //Add to the total spend
        totalSpend += orderSpend;
        //Check if customer earned new points
        if ((int) (oldTotalSpend / 100) < (int) (totalSpend / 100)) {
            points +=1;
        }

    }

    /**
        Use discount based on the points and cost of transaction
     */
    public float useDiscount(float orderSpend) {
        if (orderSpend >= points) {
            orderSpend = orderSpend - points;
            points = 0;
        } else {
            int discount = (int) orderSpend;
            orderSpend -= discount;
            this.points -= discount;
        }
        return orderSpend;
    }

    /**
        Getters and other helepr functions
     */

    public int getPoints() {
        return points;
    }

    public float getTotalSpend() {
        return totalSpend;
    }

    public String toString() {
        return "Total Spend: €" + totalSpend + " Points: " + points;
    }
}
