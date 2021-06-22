package tesis1.datos;


/**
 * Title:        Asignacion de horarios
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      ITSON
 * @author Adolfo Espinoza
 * @version 1.0
 */

public class ElementoDeSemCarr {
  public int grupo;   // Indice en tabla de grupos
  public int in;      // Nodo de entrada en arbol sem-carr
  public int out;     // Nodo de entrada en arbol sem-carr
  public int n=1;       // Numero de grupos de esta misma materia


  public ElementoDeSemCarr(int g, int i, int o ) {
    grupo=g;
    in=i;
    out=o;
  }
}