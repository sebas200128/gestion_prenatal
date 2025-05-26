package controller;

import modelo.signosVitales;

public class ControladorSignosVitales {

    public boolean registrarSignosVitales(int pacienteId, String fecha, String hora, String presionArterial,
            int frecuenciaCardiaca, int frecuenciaRespiratoria, double temperatura,
            int saturacionOxigeno, double peso, double altura, String observaciones) {
        signosVitales sv = new signosVitales(pacienteId, fecha, hora, presionArterial, frecuenciaCardiaca, frecuenciaRespiratoria, temperatura, saturacionOxigeno, peso, altura, observaciones);
        return sv.registrar();
    }
    
    
}
