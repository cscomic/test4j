package org.test4j.spec.printer;

import java.util.ArrayList;
import java.util.List;

import org.test4j.spec.ISpec;
import org.test4j.spec.inner.IScenario;
import org.test4j.spec.inner.ISpecPrinter;

public class MoreSpecPrinter implements ISpecPrinter {
    final List<ISpecPrinter> printers = new ArrayList<ISpecPrinter>();

    public MoreSpecPrinter() {
        printers.add(new ConsolePrinter());
        printers.add(new HtmlFilePrinter());
    }

    public void addSpecPrinter(ISpecPrinter printer) {
        printers.add(printer);
    }

    @Override
    public void printSummary(Class<? extends ISpec> spec) {
        for (ISpecPrinter printer : printers) {
            printer.printSummary(spec);
        }
    }

    @Override
    public void printScenario(ISpec spec, IScenario scenario) {
        for (ISpecPrinter printer : printers) {
            printer.printScenario(spec, scenario);
        }
    }
}
