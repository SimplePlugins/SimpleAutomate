/*
MIT License

Copyright (c) 2018 SimplePlugins

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

Written by Matthew Hogan <matt@matthogan.co.uk>, June 2017
*/
package co.uk.matthogan.simpleplugins.simpleautomate;

import lombok.AllArgsConstructor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * @author Matthew Hogan
 * @since 01-06-18
 */
public class SimpleAutomatePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        for (String key : this.getConfig().getKeys(false)) {
            List<String> commands = this.getConfig().getStringList(key);
            int seconds = Integer.parseInt(key);

            new AutomatedTask(seconds, commands, this).startTimer();
        }
    }

    @AllArgsConstructor
    private class AutomatedTask extends BukkitRunnable {

        private int seconds;
        private List<String> commands;
        private Plugin plugin;

        @Override
        public void run() {
            this.commands.forEach((command) -> Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(), command
            ));
        }

        private void startTimer() {
            int interval = 20 * this.seconds;
            this.runTaskTimer(this.plugin, interval, interval);
        }
    }
}