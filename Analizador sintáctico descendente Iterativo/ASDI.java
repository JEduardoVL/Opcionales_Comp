import java.util.List;
import java.util.Stack;

public class ASDI implements Parser {

    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;
    private final TablaAnalisis tablaAnalisis;
    private final Stack<TipoToken> pila = new Stack<>();

    public ASDI(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
        this.tablaAnalisis = new TablaAnalisis();
    }

    @Override
    public boolean parse() {
        pila.push(TipoToken.EOF); // Marcador de fin de pila
        pila.push(TipoToken.SELECT); // Símbolo de inicio de tu gramática
        
        while (!pila.isEmpty()) {
            TipoToken tope = pila.peek();
            
            if (isTerminal(tope)) {
                if (tope == preanalisis.tipo) {
                    pila.pop();
                    advance();
                } else {
                    hayErrores = true;
                    System.out.println("Error de coincidencia. Esperado: " + tope + ", encontrado: " + preanalisis.tipo);
                    return false;
                }
            } else {
                List<Enum<?>> produccion = tablaAnalisis.getProduccion(tope, preanalisis.tipo);
                if (produccion != null) {
                    pila.pop(); // Quita el no-terminal
                    
                    // Inserta la producción en la pila en orden inverso
                    for (int j = produccion.size() - 1; j >= 0; j--) {
                        pila.push((TipoToken) produccion.get(j));
                    }
                } else {
                    hayErrores = true;
                    System.out.println("Error de sintaxis en token " + preanalisis.tipo);
                    return false;
                }
            }
        }

        if (!hayErrores && preanalisis.tipo == TipoToken.EOF) {
            System.out.println("Consulta correcta");
            return true;
        } else {
            System.out.println("Se encontraron errores");
            return false;
        }
    }

    private void advance() {
        i++;
        if (i < tokens.size()) {
            preanalisis = tokens.get(i);
        } else {
            preanalisis = new Token(TipoToken.EOF, "", -1);
        }
    }

    private boolean isTerminal(TipoToken token) {
        // Deberás completar esto basado en tu conjunto de terminales
        return token == TipoToken.SELECT || token == TipoToken.FROM || token == TipoToken.DISTINCT || token == TipoToken.ASTERISCO || token == TipoToken.COMA || token == TipoToken.PUNTO || token == TipoToken.IDENTIFICADOR || token == TipoToken.EOF;
    }
}
