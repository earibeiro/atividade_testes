package paranavai.calendario;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;


public class Calendario {
    

    public String getCalendario() {
        Calendar cal = Calendar.getInstance();
        int mes = cal.get(Calendar.MONTH) + 1;
        int ano = cal.get(Calendar.YEAR);
        return gerarCalendarioMesAno(mes, ano);
    }
    

    public String getCalendario(String ano) {
        validarAno(ano);
        return gerarCalendarioAno(Integer.parseInt(ano));
    }
    

    public String getCalendario(String mes, String ano) {
        validarMes(mes);
        validarAno(ano);
        return gerarCalendarioMesAno(Integer.parseInt(mes), Integer.parseInt(ano));
    }
    

    public String getCalendario(String... variosParametros) {
        if (variosParametros.length == 0) {
            return getCalendario();
        } else if (variosParametros.length == 1) {
            return getCalendario(variosParametros[0]);
        } else {
            return getCalendario(variosParametros[0], variosParametros[1]);
        }
    }
    
    
    private void validarMes(String mesStr) {
        try {
            int mes = Integer.parseInt(mesStr);
            if (mes < 1 || mes > 12) {
                throw new IllegalArgumentException("Mês deve estar entre 1 e 12");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Mês deve ser um número válido");
        }
    }
    
    private void validarAno(String anoStr) {
        try {
            int ano = Integer.parseInt(anoStr);
            if (ano < 1 || ano > 9999) {
                throw new IllegalArgumentException("Ano deve estar entre 1 e 9999");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ano deve ser um número válido");
        }
    }
    
    private String gerarCalendarioMesAno(int mes, int ano) {
        YearMonth ym = YearMonth.of(ano, mes);
        StringBuilder sb = new StringBuilder();
        
        sb.append(getNomeMes(mes)).append(" ").append(ano).append("\n");
        sb.append("Do Se Te Qa Qi Se Sa\n");
        
        boolean reformaGregoriana = (ano == 1752 && mes == 9);
        
        LocalDate inicioMes = LocalDate.of(ano, mes, 1);
        int diaSemana = inicioMes.getDayOfWeek().getValue() % 7;
        
        for (int i = 0; i < diaSemana; i++) {
            sb.append("   ");
        }
        
        int ultimoDia = ym.lengthOfMonth();
        for (int dia = 1; dia <= ultimoDia; dia++) {
            if (reformaGregoriana && dia >= 3 && dia <= 13) {
                continue;
            }
            
            sb.append(String.format("%2d ", dia));
            
            if ((dia + diaSemana) % 7 == 0 || dia == ultimoDia) {
                sb.append("\n");
            }
        }
        
        return sb.toString();
    }
    
    private String gerarCalendarioAno(int ano) {
        StringBuilder sb = new StringBuilder();
        sb.append("Calendário ").append(ano).append("\n\n");
        
        for (int mes = 1; mes <= 12; mes++) {
            sb.append(gerarCalendarioMesAno(mes, ano));
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    private String getNomeMes(int mes) {
        String[] nomesMeses = {
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
        return nomesMeses[mes - 1];
    }
}