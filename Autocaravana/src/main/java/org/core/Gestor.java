package org.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


//VAMOS A NECESITAR UN CONTADOR DE RESERVAS PARA PODER ASIGNAR LOS ID A LAS RESERVAS
//VAMOS A NECESITAR UN CONTADOR DE AUTOCARAVANAS PARA PODER ASIGNAR LOS ID A LAS AUTOCARAVANAS
//VAMOS A NECESITAR UN CONTADOR DE CLIENTES PARA PODER ASIGNAR LOS ID A LOS CLIENTES
//Plasmar en el modelo de mermaid

public class Gestor{
    private List<Autocaravana> autocaravanas = new ArrayList<Autocaravana>();
    private List<Reserva> reservas = new ArrayList<Reserva>();
    private List<Cliente> clientes = new ArrayList<Cliente>();
    private int cantidadAutocaravanas = 0;
    private int cantidadReservas = 0;

    public List<Autocaravana> getAutocaravanas(){
        return autocaravanas;
    }

    public List<Reserva> getReservas(){
        return reservas;
    }

    public void agregarAutocaravana (Autocaravana C){
        autocaravanas.add(C);
    }

    public Autocaravana agregarAutocaravana (String modelo, float precioPorDia)
    {
        Autocaravana C = new Autocaravana(modelo, precioPorDia);
        autocaravanas.add(C);
        return C;
    }

    public void eliminarAutocaravana (Autocaravana C){
        if(!autocaravanas.isEmpty()){
            if (!autocaravanas.remove(C))
              System.out.print("Esa Autocaravana no existe en la lista");
        }
        else System.out.print("Lista Vacia");
    }

    public Autocaravana buscarAutocaravana(int id) {

        for (Autocaravana A : autocaravanas) { //iterador
            if (A.getIdA() == id) {
                return A;
            }
        }
        return null;
    }

    //CAMBIAR MUCHAS COSAS
    public Reserva crearReserva(Autocaravana A, Cliente C, LocalDate fechInicio, LocalDate fechFin){
        Reserva R = new Reserva (cantidadReservas++,A, C, fechInicio, fechFin);

        // Validar que la fecha de inicio sea anterior a la fecha de fin
        if (fechInicio.isAfter(fechFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
        }

        //comprobar que el numero de reserva no existe
        for (Reserva iR : reservas) {
            if (iR.getIdR() == cantidadReservas) {
                throw new IllegalArgumentException("El numero de reserva ya existe.");
            }
        }

        // Validar que la autocaravana est?? disponible durante el periodo de reserva
        if (!estaDisponible(A,fechInicio, fechFin)) {
            throw new IllegalArgumentException("La autocaravana no est?? disponible para el periodo seleccionado.");
        }

        //Si en clientes no hay nadie con el mismo id que el cliente, lo a??adimos
        if (!clientes.contains(C)) {
            clientes.add(C);
        }

        //Si la caravana no esta en autocaravanas, la a??adimos
        if (!autocaravanas.contains(A)) {
            autocaravanas.add(A);
        }

        // Agregar la reserva a la lista de reservas
        reservas.add(R);

        // Devolver la reserva creada
        return R;
    }

    private Reserva crearReservaArreglada(int numReservas, Autocaravana A, Cliente C, LocalDate fechInicio, LocalDate fechFin){
        Reserva R = new Reserva (numReservas,A, C, fechInicio, fechFin);

        // Validar que la fecha de inicio sea anterior a la fecha de fin
        if (fechInicio.isAfter(fechFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
        }

        // Validar que la autocaravana est?? disponible durante el periodo de reserva
        if (!estaDisponible(A,fechInicio, fechFin)) {
            throw new IllegalArgumentException("La autocaravana no est?? disponible para el periodo seleccionado.");
        }

        //comprobar que el numero de reserva no existe
        for (Reserva iR : reservas) {
            if (iR.getIdR() == numReservas) {
                throw new IllegalArgumentException("El numero de reserva ya existe.");
            }
        }

        //Si el cliente no esta en clientes, lo a??adimos
        if (!clientes.contains(C)) {
            clientes.add(C);
        }

        //Si la caravana no esta en autocaravanas, la a??adimos
        if (!autocaravanas.contains(A)) {
            autocaravanas.add(A);
        }

        // Agregar la reserva a la lista de reservas
        reservas.add(R);

        // Devolver la reserva creada
        return R;
    }

    public Cliente crearCliente(String nombre, String apellidos, String dni, String email){
        Cliente C = new Cliente (nombre,dni, apellidos, email);
        clientes.add(C);
        return C;
    }

    public void crearCliente(Cliente C){
        //Comprobar si el cliente ya existe en clientes
        for (Cliente iC : clientes) {
            if (iC.getIdC() == (C.getIdC())) {
                return;
            }
        }
        clientes.add(C);
    }
    private boolean estaDisponible(Autocaravana A, LocalDate fechInicio, LocalDate fechFin){
        for (Reserva R : reservas) {
            if (R.getAutocaravana().equals(A)) {
                if (R.getFechaIni().isBefore(fechFin) && R.getFechaFin().isAfter(fechInicio)) {
                    return false;
                }
            }
        }

        return true;
    }

    public Cliente buscarCliente(int id) {

        for (Cliente C : clientes) { //iterador
            if (C.getIdC() == id) {
                return C;
            }
        }
        return null;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    //Funcion que muestre por pantalla una prueba de la clase
    public void mostrar(){
        System.out.println("Autocaravanas: ");
        for (Autocaravana A : autocaravanas) {
            System.out.println(A.getIdA());
        }
        System.out.println("Reservas: ");
        for (Reserva R : reservas) {
            System.out.println(R.getIdR());
        }
    }

    public void eliminarCliente(Cliente C) {
        if(!clientes.isEmpty()){
            if (!clientes.remove(C))
                System.out.print("Ese Cliente no existe en la lista");
        }
        else System.out.print("Lista Vacia");
    }

    public void eliminarReserva(Reserva R) {
        if(!reservas.isEmpty()){
            if (!reservas.remove(R))
                System.out.print("Esa Reserva no existe en la lista");
        }
        else System.out.print("Lista Vacia");
    }

    public Reserva buscarReserva(int id) {

        for (Reserva R : reservas) { //iterador
            if (R.getIdR() == id) {
                return R;
            }
        }
        return null;
    }

    public void modificarCliente(Cliente C, String nombre, String apellidos ,String dni, String email){
    //buscar cliente en reservas y cambiarlo
    C.modificarCliente(nombre, apellidos, dni, email);
    //buscar C en reservas y cambiarlo
    for (Reserva R : reservas) {
        if (R.getCliente().getIdC() == (C.getIdC())) {
            eliminarReserva(R);
            crearReservaArreglada(R.getIdR(),R.getAutocaravana(), C, R.getFechaIni(), R.getFechaFin());
            break;
        }
    }
        }


    public void modificarAutocaravana(Autocaravana A, String modelo, float precioPorDia){
        A.modificarAutocaravana(modelo, precioPorDia);
        //buscar A en reservas y cambiarlo
        for (Reserva R : reservas) {
            if (R.getAutocaravana().getIdA() == (A.getIdA())) {
                eliminarReserva(R);
                crearReservaArreglada(R.getIdR(),A, R.getCliente(), R.getFechaIni(), R.getFechaFin());
                break;
            }
        }
    }

    public void modificarReserva(Reserva R, Autocaravana A, Cliente C, LocalDate fechInicio, LocalDate fechFin){
          Reserva R2 = new Reserva(R.getIdR(),A, C, fechInicio, fechFin);
          eliminarReserva(R);
          reservas.add(R2);
    }

//funciones de ordenar las listas segun sus parametros
    void ordenarclientesIdAsc(){
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c1.getIdC() - c2.getIdC();
            }
        });
    }

    void ordenarclientesIdDesc() {
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c2.getIdC() - c1.getIdC();
            }
        });
    }

        void ordenarclientesNombreAsc(){
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c1.getNombre().compareTo(c2.getNombre());
            }
        });
    }

        void ordenarclientesNombreDesc(){
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c2.getNombre().compareTo(c1.getNombre());
            }
        });
        }

        void ordenarclientesApellidosAsc(){
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c1.getApellido().compareTo(c2.getApellido());
            }
        });
        }

        void ordenarclientesApellidosDesc(){
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c2.getApellido().compareTo(c1.getApellido());
            }
        });
        }

        void ordenarclientesDniAsc(){
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c1.getDni().compareTo(c2.getDni());
            }
        });
        }

        void ordenarclientesDniDesc(){
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c2.getDni().compareTo(c1.getDni());
            }
        });
        }

        void ordenarclientesEmailAsc(){
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c1.getEmail().compareTo(c2.getEmail());
            }
        });
        }

        void ordenarclientesEmailDesc(){
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c2.getEmail().compareTo(c1.getEmail());
            }
        });
        }

        void ordenarAutocaravanasIdAsc(){
    Collections.sort(autocaravanas, new Comparator<Autocaravana>() {
            @Override
            public int compare(Autocaravana a1, Autocaravana a2) {
                return a1.getIdA() - a2.getIdA();
            }
        });
}
    void ordenarAutocaravanasIdDesc(){
    Collections.sort(autocaravanas, new Comparator<Autocaravana>() {
            @Override
            public int compare(Autocaravana a1, Autocaravana a2) {
                return a2.getIdA() - a1.getIdA();
            }
        });
    }

    void ordenarAutocaravanasModeloAsc(){
    Collections.sort(autocaravanas, new Comparator<Autocaravana>() {
            @Override
            public int compare(Autocaravana a1, Autocaravana a2) {
                return a1.getModelo().compareTo(a2.getModelo());
            }
        });
    }

    void ordenarAutocaravanasModeloDesc(){
    Collections.sort(autocaravanas, new Comparator<Autocaravana>() {
            @Override
            public int compare(Autocaravana a1, Autocaravana a2) {
                return a2.getModelo().compareTo(a1.getModelo());
            }
        });
    }

    void ordenarAutocaravanasPrecioAsc(){
    Collections.sort(autocaravanas, new Comparator<Autocaravana>() {
            @Override
            public int compare(Autocaravana a1, Autocaravana a2) {
                return (int) (a1.getPrecioPorDia() - a2.getPrecioPorDia());
            }
        });
    }

    void ordenarAutocaravanasPrecioDesc(){
    Collections.sort(autocaravanas, new Comparator<Autocaravana>() {
            @Override
            public int compare(Autocaravana a1, Autocaravana a2) {
                return (int) (a2.getPrecioPorDia() - a1.getPrecioPorDia());
            }
        });
    }

    void ordenarReservasIdAsc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r1.getIdR() - r2.getIdR();
            }
        });
    }

    void ordenarReservasIdDesc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r2.getIdR() - r1.getIdR();
            }
        });
    }

    void ordenarReservasFechaIniAsc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r1.getFechaIni().compareTo(r2.getFechaIni());
            }
        });
    }

    void ordenarReservasFechaIniDesc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r2.getFechaIni().compareTo(r1.getFechaIni());
            }
        });
    }

    void ordenarReservasFechaFinAsc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r1.getFechaFin().compareTo(r2.getFechaFin());
            }
        });
    }

    void ordenarReservasFechaFinDesc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r2.getFechaFin().compareTo(r1.getFechaFin());
            }
        });
    }

    void ordenarReservasPrecioAsc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return (int) (r1.getPrecioTotal() - r2.getPrecioTotal());
            }
        });
    }

    void ordenarReservasPrecioDesc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return (int) (r2.getPrecioTotal() - r1.getPrecioTotal());
            }
        });
    }


    void ordenarReservasClienteAsc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r1.getCliente().getDni().compareTo(r2.getCliente().getDni());
            }
        });
    }

    void ordenarReservasClienteDesc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r2.getCliente().getDni().compareTo(r1.getCliente().getDni());
            }
        });
    }

    void ordenarReservasAutocaravanaAsc(){
Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r1.getAutocaravana().getIdA() - r2.getAutocaravana().getIdA();
            }
        });
    }

    void ordenarReservasAutocaravanaDesc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r2.getAutocaravana().getIdA() - r1.getAutocaravana().getIdA();
            }
        });
    }

    void ordenarReservasEstadoAsc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r1.getEstado().compareTo(r2.getEstado());
            }
        });
    }

    void ordenarReservasEstadoDesc(){
    Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r2.getEstado().compareTo(r1.getEstado());
            }
        });
    }

}
