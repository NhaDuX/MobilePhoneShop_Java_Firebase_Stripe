package edu.huflit.shopDT.interfaces;

public interface ClickListener {
    void onIncreaseClick(int position, double newTotal);
    void onDecreaseClick(int position, double newTotal);
}
