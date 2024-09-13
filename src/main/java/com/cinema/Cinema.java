package com.cinema;

import java.util.ArrayList;

/**
 * Clase que representa una sala de cine.
 */
public class Cinema {
    /**
     * seats = [[Seat (tiene fila y numero y si esta ocupada o no), Seat, Seat],[Seat, Seat, Seat]]
     */
    private Seat[][] seats;

    /**
     * Construye una sala de cine. Se le pasa como dato un arreglo cuyo tamaño
     * es la cantidad de filas y los enteros que tiene son el número de butacas en cada fila.
     */
    public Cinema(int[] rows) {
        seats = new Seat[rows.length][];
        initSeats(rows);
    }

    /**
     * Inicializa las butacas de la sala de cine.
     *
     * @param rows arreglo que contiene la cantidad de butacas en cada fila
     */
    private void initSeats(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            seats[i] = new Seat[rows[i]];
        }
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }

    /**
     * Cuenta la cantidad de seats disponibles en el cine.
     */
    public int countAvailableSeats() {
        int count = 0;
        for (int fila = 0; fila < seats.length; fila++){
            for (int butaca = 0; butaca < seats[fila].length; butaca++){
                if (seats[fila][butaca].isAvailable()){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Busca la primera butaca libre dentro de una fila o null si no encuentra.
     */
    public Seat findFirstAvailableSeatInRow(int row) {
        if (row > seats.length || row < 0){
            return null;}
        for (Seat butaca : seats[row]){
            if (butaca.isAvailable()){
                return butaca;
            }
        }
        return null;
    }

    /**
     * Busca la primera butaca libre o null si no encuentra.
     */
    public Seat findFirstAvailableSeat() {
        for (int fila = 0; fila < seats.length; fila++){
            for (int butaca = 0; butaca < seats[fila].length; butaca++){
                if (seats[fila][butaca].isAvailable()){
                    return seats[fila][butaca];
                }
            }
        }
        return null;
    }

    /**
     * Busca las N butacas libres consecutivas en una fila. Si no hay, retorna null.
     *
     * @param row    fila en la que buscará las butacas.
     * @param amount el número de butacas necesarias (N).
     * @return La primer butaca de la serie de N butacas, si no hay retorna null.
     */
    public Seat getAvailableSeatsInRow(int row, int amount) {
        int count = 0;
        for (Seat butaca : seats[row]){
            if (butaca.isAvailable()){
                count++;
                if (count == amount){
                    int num = butaca.getSeatNumber() - amount + 1;
                    return seats[row][num];
                }
            }
            else{
                count = 0;
            }
        }
        return null;
    }

    /**
     * Busca en toda la sala N butacas libres consecutivas. Si las encuentra
     * retorna la primer butaca de la serie, si no retorna null.
     *
     * @param amount el número de butacas pedidas.
     */
    public Seat getAvailableSeats(int amount) {
        int count = 0;
        for (int fila = 0; fila < seats.length; fila++){
            for (Seat butaca : seats[fila]){
                if (butaca.isAvailable()){
                    count++;
                    if (count == amount){
                        int num = butaca.getSeatNumber() - amount + 1;
                        return seats[fila][num];
                    }
                }
                else{
                    count = 0;
                }
            }
        }
        return null;
    }

    /**
     * Marca como ocupadas la cantidad de butacas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a reservar.
     */
    public void takeSeats(Seat seat, int amount) {
        int fila = seat.getRow();
        int num = seat.getSeatNumber();
        if (amount > seats[fila].length){
            throw new ArrayIndexOutOfBoundsException();
        }
        for (Seat butaca : seats[fila]){
            if (butaca.getSeatNumber() >= num && butaca.getSeatNumber() <= num + amount - 1){
                butaca.takeSeat();
            }
        }
    }

    /**
     * Libera la cantidad de butacas consecutivas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a liberar.
     */
    public void releaseSeats(Seat seat, int amount) {
        int fila = seat.getRow();
        int num = seat.getSeatNumber();
        if (amount > seats[fila].length){
            throw new ArrayIndexOutOfBoundsException();
        }
        for (Seat butaca : seats[fila]){
            if (butaca.getSeatNumber() >= num && !butaca.isAvailable()){
                butaca.releaseSeat();
            }
        }
    }
}