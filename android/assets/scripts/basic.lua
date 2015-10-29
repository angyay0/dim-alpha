--
-- Created by AIMOS STUDIO
-- User: Angel
-- Date: 28/10/2015
-- Time: 10:47 AM
--

--
-- Consideraciones ia
-- ->Si hay vida ataca, si no cubre
-- ->Behavior dependiente del arma
-- ->LowHealth=Cubre
--        ->Bosses atacan a lo verga inteligente
-- ->Bosses spawnean en checks
-- ->Spawneados en checks shielded
--

-- Sense if can jump and if it have to
function jumperBehavior(chrtr,p)
    if chrtr:getY()+(20/getPTM()) < p:getY() then
        chrtr:jump();
    end
end

-- Evaluate and set Direction according to player
function evaluateDir(chrtr)
    plyr = chrtr:getPlay():getPlayer()
    if plyr:getDirection()then
        chrtr:setDirection(false)
    else
        chrtr:setDirection(true)
    end
   -- return chrtr
end

-- Evaluate if should move to player or is on range
function moveOrisInRange(chrtr,p)
    if chrtr:getX() > p:getX()+(70/getPTM()) then
        return 1 --chrtr:move(false)
    elseif chrtr:getX() < p:getX()-(70/getPTM()) then
        return 2 --chrtr:move(true)
    else
        return 0 --inRange = true
    end
end

-- Generates a coward movement
function moveCoward(chrtr,p)
    if chrtr:getX() < p:getX()+(150/getPTM())  then
        return 2 --chrtr:move(false)
    end

    return 0
end

-- Run like Bitch Behavior
function runLikeBitch(chrtr,p)
    if chrtr:getX() < p:getX()+(250/getPTM())  then
        return 2 --chrtr:move(false)
    end

    return 0
end

-- Coward statement to know if go or hide like a little pussy
function hideMe(chrtr,val)
    if chrtr:getHP() < val then
        return true
    end

    return false
end

-- Function to detect if game is currently in execution mode
function canPlay(gamestate)
    if gamestate == 1 or gamestate == 5 then
        return true
    end

    return false
end

-- Obtain the current constant for setting cordinates in Box2D
function getPTM()
    return 100.0
end

-- Sense if character has weapon and select an identificator
-- to execute a correct beahavior according to weapon ID
function senseWeapon(chrtr)
    if chrtr:hasWeapon() then
        return chrtr:getWeapon():getWeaponType()
    else
        return 0
    end
end
