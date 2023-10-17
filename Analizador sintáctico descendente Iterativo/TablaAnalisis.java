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

        // Por A1 → , A
        putProduccion(TipoToken.COMA, TipoToken.COMA, Arrays.asList(TipoToken.COMA, TipoNoTerminal.A));

        // Por A2 → id A3
        putProduccion(TipoToken.IDENTIFICADOR, TipoToken.IDENTIFICADOR, Arrays.asList(TipoToken.IDENTIFICADOR, TipoNoTerminal.A3));

        // Por A3 → . id
        putProduccion(TipoToken.PUNTO, TipoToken.PUNTO, Arrays.asList(TipoToken.PUNTO, TipoToken.IDENTIFICADOR));

        // Por T → T1 T2
        putProduccion(TipoToken.IDENTIFICADOR, TipoToken.IDENTIFICADOR, Arrays.asList(TipoNoTerminal.T2, TipoNoTerminal.T1));

        // Por T1 → , T
        putProduccion(TipoToken.COMA, TipoToken.COMA, Arrays.asList(TipoToken.COMA, TipoNoTerminal.T));

        // Por T2 → id T3
        putProduccion(TipoToken.IDENTIFICADOR, TipoToken.IDENTIFICADOR, Arrays.asList(TipoToken.IDENTIFICADOR, TipoNoTerminal.T3));

        // Por T3 → id
        putProduccion(TipoToken.IDENTIFICADOR, TipoToken.IDENTIFICADOR, Arrays.asList(TipoToken.IDENTIFICADOR));

//----------------------------------------------------------------------------------------------------------------------------------
        // Por A1 → E


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
