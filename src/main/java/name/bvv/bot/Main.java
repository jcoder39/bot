package name.bvv.bot;

import name.bvv.bot.attack.Attack;
import name.bvv.bot.attack.http.DoS;

/**
 * Created by User on 22.07.2016.
 */
public class Main
{
    public static void main(String[] args)
    {
        Attack attack = new DoS();
        attack.kill();
    }
}
