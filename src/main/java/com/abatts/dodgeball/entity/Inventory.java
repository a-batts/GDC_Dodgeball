package com.abatts.dodgeball.entity;

public class Inventory<E> {
    private E handLeft;
    private E handRight;

    private int size = 0;

    public boolean contains(Object o){
        return handLeft == o || handRight == o;
    }

    public E drop(E o){
        if (handLeft == o){
            handLeft = null;
            size--;
            return o;
        }
        else if (handRight == o){
            handRight = null;
            size--;
            return o;
        }
        return null;
    }

    public void empty(){
        handLeft = null;
        handRight = null;
        size = 0;
    }

    public E getFirst(){
        if (handLeft != null)
            return handLeft;
        return handRight;
    }

    public boolean hasEmptyHand(){
        return handLeft == null || handRight == null;
    }

    public void take(E o){
        if (handLeft == null){
            handLeft = o;
            size++;
        }
        else if (handRight == null){
            handRight = o;
            size++;
        }
    }

    public int size(){
        return size;
    }
}
