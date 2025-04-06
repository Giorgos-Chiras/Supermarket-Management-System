package Customer;

public class BonusCard {
    private int points;
    private float totalSpend;

    public BonusCard(int points, float totalSpend) {
        this.points = points;
        this.totalSpend = totalSpend;
    }

    public void updateBonusCard(float orderSpend) {
        float oldTotalSpend = totalSpend;
        totalSpend += orderSpend;
        if ((int) (oldTotalSpend / 100) < (int) (totalSpend / 100)) {
            points +=1;
        }

    }

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
