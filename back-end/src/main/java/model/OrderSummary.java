package model;

public class OrderSummary {
    private String userName;
    private String session; // 如果需要，可以根據你的資料結構來設置
    private int count;

    public OrderSummary(String userName, String session, int count) {
        this.userName = userName;
        this.session = session;
        this.count = count;
    }

    // Getter 和 Setter 方法
    public String getUserName() {
        return userName;
    }

    public String getSession() {
        return session;
    }

    public int getCount() {
        return count;
    }
}

