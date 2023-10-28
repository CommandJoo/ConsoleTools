package de.jo.modules;

import de.jo.util.PackageScanner;
import de.jo.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class ModuleManager {

    private List<Module> modules = new ArrayList<>();

    public ModuleManager() {

        loadModules("de.jo.modules.impl");
    }

    private void loadModules(String pckg) {
        for(Object obj : PackageScanner.classes(pckg, ModuleInfo.class)) {
            if(obj instanceof Class<?>) {
                Class<?> c = (Class<?>) obj;
                List<Class<?>> interfaces = Arrays.asList(c.getInterfaces());
                if(interfaces.size() == 0) return;
                if(interfaces.contains(Module.class)) {
                    try {
                        Module mod = (Module) c.newInstance();
                        modules.add(mod);
                    } catch(Exception ex) {
                        Strings.error("Couldn't create Module: "+c.getName());
                        Strings.error(new StackTraceElement("ModuleManager", "loadModules", "ModuleManager.java", 37).toString());
                        System.err.println("Error found! Couldn't create Module: "+c.getName());
                    }
                }
            }
        }
    }

    public List<Module> modules() {
        return modules;
    }
    public Module module(String name) {
        for(Module module : modules) {
            ModuleInfo info = moduleInfo(module);
            if(isModule(name, info)) return module;
        }
        return null;
    }
    public boolean isModule(String name, ModuleInfo info) {
        if(info.name().toLowerCase().equals(name)) {
            return true;
        }
        for(String s : info.aliases()) {
            if(s.toLowerCase().equals(name)) return true;
        }
        return false;
    }
    public ModuleInfo moduleInfo(Module mod) {
        return mod.getClass().getAnnotation(ModuleInfo.class);
    }

}
