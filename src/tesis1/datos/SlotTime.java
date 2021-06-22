package tesis1.datos;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

/** Un objeto <code>SlotTime</code> define un tipo de horario y los periodos
 *  de medias horas que ocupa dicho horario.
 *
*/
public class SlotTime implements Cloneable{

  private  int RENGLS=12, COLS= 5;

  /** Es la decripci�n del tipo de horario  ejemplo: LuMiVi(1.0).*/
  private String descripcion;

  /** Es el desplazamiento que existe entre la hora inicial y la hora en que
   *  esta asignado el SlotTime  (en medias horas) */
  private short offset;

  private short horaInic=7;

  /**  Es la distribucion en medias horas del horario, bit 0 es lunes,
  * primer byte es la primer media hora (dada por "hora inicial+offset)"*/
  private byte [][] mascara = new byte [RENGLS][COLS];

  /** Tipo de horario al que pertenece esta variante, lo usa como referencia
   *  la materia */
  private short tipo;

  private short turno; /* 1=matutino (7 a 14 Hrs), 2=Vespert(14 a 21 hrs)
                          3= Los 2 turnos,  0= no definido*/


  public SlotTime() {
  }

  public Object clone(){
    SlotTime aux = new SlotTime();
    aux.descripcion= new String(this.descripcion) ;
    aux.offset=this.offset;
    aux.tipo=this.tipo;
    aux.turno= this.turno;
    for( int r=0; r<RENGLS; r++)for(int c=0; c<COLS; c++){
      aux.mascara[r][c]=this.mascara[r][c];
    }
    return aux;
  }

  public void setTipo(short t){   tipo=t;  }
  public short getTipo(){ return tipo;  }
  public void setOffset(short t){   offset=t;  }
  public short getOffset(){ return offset;  }

  public void setTurno(){
    int horInic=0, horFin=0;
    buscaInic:
    for(int i=0; i<RENGLS; i++) for(int j=0; j<COLS; j++){
      if(mascara[i][j]==1) {
        horInic=i+this.offset;
        break buscaInic;
      }
    }
    buscaFin:
    for(int i=RENGLS-1; i>=0; i--) for(int j=0; j<COLS; j++){
      if(mascara[i][j]==1) {
        horFin=i+this.offset;
        break buscaFin;
      }
    }
    if((horInic>=0)&&(horInic<=13)&&(horFin>=0)&&(horFin<=13)){
      this.turno=1;// turno matutino entre 7  y 14
    }
    if((horInic>=14)&&(horInic<=27)&&(horFin>=14)&&(horFin<=27)){
      this.turno=2;
    }
    if((horInic>=0)&&(horInic<=13)&&(horFin>=14)&&(horFin<=27)){
      this.turno=3;
    }
  }

  public short getTurno(){ return turno;  }

  public String getOffsetStringHora(){
    String hora = new String( new Integer(offset/2 + horaInic).toString() );
    if((offset%2) == 1) hora = new String(hora+"*");
    return( hora );
  }


  public void setDescripcion(String t){   descripcion=t;  }
  public String getDescripcion(){ return descripcion;  }

  public void setMascara(int r, int c, byte dato){   mascara[r][c]=dato;  }

  public void setMascara(String s){
    /*Cuando solo se podian definir horarios menores de 3hrs continuas
    byte auxByteArray[][] = new byte[RENGLS][COLS];//texto hexa a long
    long lg = Long.parseLong(s.substring(0,s.length()-1),16);
    int cont=0, r=0, c=0;
    while(lg > 0){
      long aux;
      aux=lg%2;
      if(c<COLS)auxByteArray[r][c] = (byte)aux;
      lg = lg/2;
      if(++c==8){ c = 0; r++ ;}
    }
    mascara = auxByteArray;*/

    int r=0, c=0;
    String sAux;
    s=s.substring(0,s.length()-1);
    s="000000000000000000000000".concat(s);
    s=s.substring(s.length()-24,s.length());
    do{
        sAux=s.substring(s.length()-2,s.length());
        s=s.substring(0,s.length()-2);
        byte bAux=Byte.parseByte(sAux,16);
        for(c=0; c<5; c++){
          mascara[r][c]= (byte)(bAux%2);
          bAux = (byte)(bAux/2);
        }
        r++;
    }while(s.length()>0);//********
  }

  public byte getMascara(int r, int c){ return mascara[r][c];  }

  public int[][] getMascaraArray(){
    int [][] arreglo = new int [RENGLS][COLS];
    int r, c;
    for(r=0; r<RENGLS; r++)for(c=0; c<COLS; c++){
        arreglo[r][c]=this.mascara[r][c];
    }
    return arreglo;
  }

  public boolean isEmpalme(SlotTime st){
    SlotTime ante, post;
    boolean empalme=false;
    if(this.offset<=st.getOffset()){ ante=this;  post=st;
    }else{ante=st;  post=this;}

    int inic = post.getOffset()-ante.getOffset();

    if(inic<RENGLS){
      int r, c;
      for(r=inic; r<RENGLS; r++) for(c=0; c<COLS; c++){
          if(ante.getMascara(r,c)+post.getMascara(r-inic,c)>1)
            empalme=true;
      }
    }
    return empalme;
  }
}