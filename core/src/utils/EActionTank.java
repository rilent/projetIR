package utils;

public enum EActionTank {
Shot("S"),
Right("R"),
Left("L"),
Nothing("N");

private String name = "";


//Constructeur
EActionTank(String name){
  this.name = name;
}
 
public String toString(){
  return name;
}
}