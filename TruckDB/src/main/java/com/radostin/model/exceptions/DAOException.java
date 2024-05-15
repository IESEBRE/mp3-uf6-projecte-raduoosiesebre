package com.radostin.model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class DAOException extends Exception{

    private static final Map<Integer, String> messages = new HashMap<>();
    //num i retorna string, el map
    static {
        messages.put(0, "Error al connectar a la BD!!");
        messages.put(1, "Restricció d'integritat violada - clau primària duplicada");
        messages.put(904, "Nom de columna no vàlid");
        messages.put(936, "Falta expressió en l'ordre SQL");
        messages.put(942, "La taula o la vista no existeix");
        messages.put(1000, "S'ha superat el nombre màxim de cursors oberts");
        messages.put(1400, "Inserció de valor nul en una columna que no permet nuls");
        messages.put(1403, "No s'ha trobat cap dada");
        messages.put(1722, "Ha fallat la conversió d'una cadena de caràcters a un número");
        messages.put(1747, "El nombre de columnes de la vista no coincideix amb el nombre de columnes de les taules subjacents");
        messages.put(4091, "Modificació d'un procediment o funció en execució actualment");
        messages.put(6502, "Error numèric o de valor durant l'execució del programa");
        messages.put(12154, "No s'ha pogut resoldre el nom del servei de la base de dades Oracle o l'identificador de connexió");
        messages.put(2292, "S'ha violat la restricció d'integritat -  s'ha trobat un registre fill");
    }

    //atribut
    private int type;

    //constructor al q pasem tipo
    public DAOException(int type){
        this.type = type;
    }

    //sobreescrivim el get message
    @Override
    public String getMessage(){
        return messages.get(this.type); //el missatge del tipo
    }

    public int getType() {
        return type;
    }
}