package tesis1.datos;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class Recurso {
  protected  int RENGLS=28, COLS= 5;
  private HorarioSemana factib = new HorarioSemana();
    //Matriz que indica con "0" si no esta disponible el recurso o el "numero
    //maximo" de recursos de este tipo que pueden existir a la misma hora.
  private HorarioSemana uso = new HorarioSemana();
    //Matriz que indica el numero de recursos de este tipo que se estan usando
    //en una hora determinada.
  protected short empalmes=0;

  public Recurso() {
  }

  public void setFactib(int r, int c, short valor){
    factib.set(r,c,valor);
  }
   public void setFactib(HorarioSemana hs){
    this.factib=hs;
  }
    public HorarioSemana getFactib(){
    return this.factib;
  }

  public void setUso(int r, int c, short valor){
    uso.set(r,c,valor);
  }
   public void setUso(HorarioSemana hs){
    this.uso=hs;
  }

  protected int getUso(int r, int c){
    return (int)uso.get(r,c);
  }

  public HorarioSemana getUso(){
    return this.uso;
  }

/**
 * Entrega "true" si en todas las celdas de rejilla "factibilidad" indicadas
 * por SlotTime  "ST" esta el valor uno o mayor (factible de programar) */
  public boolean isFacti(SlotTime st){
    int r, c, rRejilla;
    boolean factible=true;
    int offset=st.getOffset();

    for(r=0; r<12; r++){
      for(c=0; c<5; c++){
        rRejilla = r+offset;
        if(rRejilla<RENGLS)
          if((st.getMascara(r,c)==1)&&(factib.get(r+offset,c)==0)){
            factible=false; break;
          }
      }
      if(!factible)break;
    }
    return factible;
  }

/**
 * Entrega "true" si en todas las celdas de rejilla "uso" indicadas
 * por SlotTime  "ST" estan disponibles ((factibles - usadas) >0 ) */
  public boolean isLibre(SlotTime st){

    int r, c, rRejilla;
    boolean libre=true;
    short disponible;
    int offset=st.getOffset();

    for(r=0; r<12; r++){
      for(c=0; c<5; c++){
        rRejilla = r + offset;
        if(rRejilla<RENGLS){
          disponible = (short) (factib.get(rRejilla,c)-uso.get(rRejilla,c));
          if((st.getMascara(r,c)==1)&&(disponible<=0)){
            libre=false;
            break;
          }
        }
      }
      if(libre==false)break;
    }
    return libre;
  }

  public void asigna(SlotTime st){
    uso.add(st);
  }

  public void desAsigna(SlotTime st){
    uso.subs(st);
  }

  /**
   * Calcula el numero de medias horas empalmadas y lo guarda en la variable
     "empalmes" y lo entrega. */
  public short cuentaEmpalmes(){

    int r, c;
    short aux, empal=0;
    for(r=0; r<RENGLS; r++) for(c=0; c<COLS; c++){
      aux = (short)(uso.get(r,c)-factib.get(r,c));
      if(aux>0) empal += aux;
    }
    return empalmes=empal;
  }

  protected void desasignaTodo(){
    int r, c;
    for(r=0; r<RENGLS; r++) for(c=0; c<COLS; c++){
      uso.set(r,c,(short)0);
    }
  }
}