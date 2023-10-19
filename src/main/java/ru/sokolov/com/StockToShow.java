package ru.sokolov.com;

import ru.sokolov.models.Stock;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class StockToShow {
    String subject;
    String supplierArticle;
    int quantity;
    int quantityFull;
    int inWayFromClient;
    String color;
    List<Stock> stocks;

    List<Stock> stocksAll;

    public boolean isCoincidence() {
        return coincidence;
    }

    public void setCoincidence(boolean coincidence) {
        this.coincidence = coincidence;
    }

    boolean coincidence = false;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSupplierArticle() {
        return supplierArticle;
    }

    public void setSupplierArticle(String supplierArticle) {
        this.supplierArticle = supplierArticle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityFull() {
        return quantityFull;
    }

    public void setQuantityFull(int quantityFull) {
        this.quantityFull = quantityFull;
    }

    public int getInWayFromClient() {
        return inWayFromClient;
    }

    public void setInWayFromClient(int inWayFromClient) {
        this.inWayFromClient = inWayFromClient;
    }

    public String getColor() {
        return color;
    }

    public void setColor(int color) {
        if (color == 0) this.color = "black"; else this.color = "white";
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<Stock> getStocksAll() {
        return stocksAll;
    }

    public void setStocksAll(List<Stock> stocksAll) {
        this.stocksAll = stocksAll;
    }

    public StockToShow(String subject, String supplierArticle, int quantity, int quantityFull, int inWayFromClient, int color, List<Stock> stocks) {
        this.subject = subject;
        this.supplierArticle = supplierArticle;
        this.quantity = quantity;
        this.quantityFull = quantityFull;
        this.inWayFromClient = inWayFromClient;
        this.stocks = stocks;
        this.stocksAll = new List<Stock>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Stock> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Stock stock) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Stock> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Stock> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Stock get(int index) {
                return null;
            }

            @Override
            public Stock set(int index, Stock element) {
                return null;
            }

            @Override
            public void add(int index, Stock element) {

            }

            @Override
            public Stock remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Stock> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Stock> listIterator(int index) {
                return null;
            }

            @Override
            public List<Stock> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        if (color == 0) this.color = "black"; else this.color = "white";
    }
}
