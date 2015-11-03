--
-- Created by AIMOS STUDIO
-- User: angyay0
-- Date: 28/10/2015
-- Time: 02:44 PM
-- To change this template use File | Settings | File Templates.
--

require("basic")

--
function behavior(chrtr)
  --[[  p = chrtr:getPlay():getPlayer()
    inRange = false
    evaluateDir(chrtr)

    if chrtr:getX() > p:getX()+(70/getPTM()) then
        chrtr:move(false)
    elseif chrtr:getX() < p:getX()-(70/getPTM()) then
        chrtr:move(true)
    else
        inRange = true
    end

    jumperBehavior(chrtr,p)--]]
    cowardBehavior(chrtr)
end
--

-- Follower Behavior in same horizontal spot
-- can jump
function followerBehavior(chrtr)
    p = chrtr:getPlay():getPlayer()
    inRange = true
    evaluateDir(chrtr)
    --chrtr:move(true)
    --print( p:getX() )

    if chrtr:getX() > p:getX()+(70/getPTM()) then
        chrtr:move(false)
    elseif chrtr:getX() < p:getX()-(70/getPTM()) then
        chrtr:move(true)
    else
        inRange = true
    end

    jumperBehavior(chrtr,p)
end

-- This behavior is used for the minions of a boss or sub or officer
-- to generate mini enemy of a kind
-- The behavior is just run away from player if health is low(No Special AI)
function cowardBehavior(chrtr)
    p = chrtr:getPlay():getPlayer()
    inRange = false
    evaluateDir(chrtr)
    --chrtr:move(true)
    --print( p:getX() )
    if not hideMe(chrtr,8) then
        if moveOrisInRange(chrtr,p) == 1 then
            chrtr:move(false)
        elseif moveOrisInRange(chrtr,p) == 2 then
            chrtr:move(true)
        else
            inRange = true
        end
    else
        if moveCoward(chrtr,p) == 1 then
            chrtr:move(false)
        elseif moveCoward(chrtr,p) == 2 then
            chrtr:move(true)
        end
    end

    jumperBehavior(chrtr,p)
end
