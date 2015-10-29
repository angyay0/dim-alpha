--
-- Created by IntelliJ IDEA.
-- User: angyay0
-- Date: 28/10/2015
-- Time: 02:44 PM
-- To change this template use File | Settings | File Templates.
--

require("basic")

function behavior(chrtr)
    p = chrtr:getPlay():getPlayer()
    evaluateDir(chrtr)
    --chrtr:move(true)
    --print( p:getX() )

    if chrtr:getX() > p:getX()+(70/getPTM()) then
        chrtr:move(false)
    elseif chrtr:getX() < p:getX()-(70/getPTM()) then
        chrtr:move(true)
    end

    if chrtr:getY()+(20/getPTM()) < p:getY() then
        chrtr:jump();
    end

    --chrtr:move(true)
end

-- Follower Behavior in same horizontal spot
-- can jump
function followerBehavior(chrtr)
    p = chrtr:getPlay():getPlayer()
    evaluateDir(chrtr)
    --chrtr:move(true)
    --print( p:getX() )

    if chrtr:getX() > p:getX()+(70/getPTM()) then
        chrtr:move(false)
    elseif chrtr:getX() < p:getX()-(70/getPTM()) then
        chrtr:move(true)
    end

    if chrtr:getY()+(20/getPTM()) < p:getY() then
        chrtr:jump();
    end

    --chrtr:move(true)
end
