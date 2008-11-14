package net.stoerr.feigenbaum.symbolic;

import java.util.Arrays;
import java.util.List;

import javolution.text.Text;

import net.stoerr.feigenbaum.util.Func;

import org.jscience.mathematics.function.Function;
import org.jscience.mathematics.function.Variable;

public class UnspecifiedFunction<Arg, Val> extends Function<Arg, Val> {

    private final Variable<Func<Arg, Val>> funcvar;
    private final Variable<Arg>[] vars;

    public UnspecifiedFunction(Variable<Func<Arg, Val>> funcvar, Variable<Arg>... vars) {
        this.funcvar = funcvar;
        this.vars = vars;
    }

    @Override
    public Val evaluate() {
        /**
         * Arg[] values = new Arg[vars.length]; return
         * funcvar.get().call(values);
         */
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Variable<Arg>> getVariables() {
        return Arrays.asList(vars);
    }

    @Override
    public Text toText() {
        Text res = Text.valueOf(funcvar.getSymbol() + "(");
        for (Variable<Arg> var : vars) {
            res = res.concat(Text.valueOf(var.getSymbol() + ","));
        }
        return res.concat(Text.valueOf(")"));
    }

}
