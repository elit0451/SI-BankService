public class Account {
    private int number;
    private int money;

    public Account(int number, int money) {
        this.number = number;
        this.money = money;
    }

    public void deposit(int amount)
    {
        money = money + amount;
    }

    public void withdraw(int amount) throws Exception{
        if(money - amount < 0)
            throw new Exception("Not enough money in the account");

        money = money - amount;
    }

    public int getBalance()
    {
        return this.money;
    }
}
