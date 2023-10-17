import java.util.*;

public class TablaAnalisis {

    private final Map<TipoToken, Map<TipoToken, List<Enum<?>>>> tabla = new HashMap<>();

    public TablaAnalisis() {
        // Aquí inicializamos la tabla de análisis

        // Por Q → select D from T
        putProduccion(TipoToken.SELECT, TipoToken.SELECT, Arrays.asList(TipoToken.SELECT, TipoNoTerminal.D, TipoToken.FROM, TipoNoTerminal.T));

        // Por D → distinct P
        putProduccion(TipoToken.DISTINCT, TipoToken.DISTINCT, Arrays.asList(TipoToken.DISTINCT, TipoNoTerminal.P));

        // Por D → P
        putProduccion(TipoToken.DISTINCT, TipoToken.ASTERISCO, Arrays.asList(TipoNoTerminal.P));
        putProduccion(TipoToken.DISTINCT, TipoToken.IDENTIFICADOR, Arrays.asList(TipoNoTerminal.P));

        // Por P → *
        putProduccion(TipoToken.ASTERISCO, TipoToken.ASTERISCO, Arrays.asList(TipoToken.ASTERISCO));

        // Por P → A
        putProduccion(TipoToken.ASTERISCO, TipoToken.IDENTIFICADOR, Arrays.asList(TipoNoTerminal.A));

        // Por A → A2 A1
        putProduccion(TipoToken.IDENTIFICADOR, TipoToken.IDENTIFICADOR, Arrays.asList(TipoNoTerminal.A2, TipoNoTerminal.A1));

        

    }

    private void putProduccion(TipoToken noTerminal, TipoToken terminal, List<Enum<?>> produccion) {
        tabla
            .computeIfAbsent(noTerminal, k -> new HashMap<>())
            .put(terminal, produccion);
    }

    public List<Enum<?>> getProduccion(TipoToken noTerminal, TipoToken terminal) {
        return tabla.getOrDefault(noTerminal, new HashMap<>()).get(terminal);
    }
}
