--
-- Created by AIMOS STUDIO
-- User: Angel
-- Date: 12/10/2015
-- Time: 03:10 PM
-- To change this template use File | Settings | File Templates.
--

local Character = luajava.bindClass("org.aimos.abstractg.character.Character")
local Thread = luajava.bindClass("java.lang.Thread")
character = nil
atomic = true

node = function()

end

function behavior(chrtr)
   -- character = chrtr
   chrtr:move(false)
   chrtr:move(false)
   -- startBehavior()
end

function stop()
    atomic = false
end

function startBehavior()
    node = new node()

    while (atomic) do
        charcter:move(false)
        Thread:sleep(500)
    end

end
