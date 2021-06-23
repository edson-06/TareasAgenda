package com.example.agenda_tareas;

public class HorariosMH {
    private String id;
    private String idMateria;
    private String materia;
    private String hora;
    HorariosMH h;

    public HorariosMH(String id, String idMateria, String materia, String hora) {
        this.id = id;
        this.idMateria = idMateria;
        this.materia = materia;
        this.hora = hora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
