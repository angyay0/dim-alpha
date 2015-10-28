--
-- Created by IntelliJ IDEA.
-- User: Alumno
-- Date: 28/10/2015
-- Time: 02:44 PM
-- To change this template use File | Settings | File Templates.
--

require("basic")

function behavior(chrtr)
    p = chrtr:getPlay():getPlayer()
    --chrtr:move(true)
    if not evaluateDir(chrtr) then
     --   if p:getX()+32 < chrtr:getX() then
            chrtr:move(true)
    --    end
    else
   --    if p:getX()-32 > chrtr:getX() then
            chrtr:move(false)
   --     end
    end
end
