--
-- Created by AIMOS STUDIO
-- User: Angel
-- Date: 29/10/2015
-- Time: 02:22 PM
--

require("basic")
require("minion")

hit = false

function behavior(chrtr)
    p = chrtr:getPlay():getPlayer()
    inRange = false
    evaluateDir(chrtr)
end

function bersekerBehavior(chrtr)
    p = chrtr:getPlay():getPlayer()
    inRange = false
    evaluateDir(chrtr)

    -- Movement Section
    if chrtr:getX() > p:getX()+(70/getPTM()) then
        chrtr:move(false)
    elseif chrtr:getX() < p:getX()-(70/getPTM()) then
        chrtr:move(true)
    else
        inRange = true
    end

    jumperBehavior(chrtr,p)
    -- Movement Section

    -- Attack Section
    if inRange then
        chrtr:damage(p)
    end
    -- Attack Section
end

function harBehavior(chrtrt)
    p = chrtr:getPlay():getPlayer()
    inRange = false
    evaluateDir(chrtr)

    -- Movement Section
    if not hit then
        if chrtr:getX() > p:getX()+(70/getPTM()) then
            chrtr:move(false)
        elseif chrtr:getX() < p:getX()-(70/getPTM()) then
            chrtr:move(true)
        else
            inRange = true
        end
    else
        if runLikeBitch(chrtr,p) == 1 then
            chrtr:move(false)
        elseif moveCoward(chrtr,p) == 2 then
            chrtr:move(true)
        else
            hit = false
        end
    end

    jumperBehavior(chrtr,p)
    -- Movement Section

    -- Attack Section
    if inRange then
        chrtr:damage(p)
        hit = true
    end
    -- Attack Section
end

function annoyingBehavior(chrtr)
    p = chrtr:getPlay():getPlayer()
    inRange = false
    evaluateDir(chrtr)

    -- Movement Section
    if not hit then
        if chrtr:getX() > p:getX()+(70/getPTM()) then
            chrtr:move(false)
        elseif chrtr:getX() < p:getX()-(70/getPTM()) then
            chrtr:move(true)
        else
            inRange = true
        end
    else
        if runLikeBitch(chrtr,p) == 1 then
            chrtr:move(false)
        elseif moveCoward(chrtr,p) == 2 then
            chrtr:move(true)
        else
            hit = false
        end
    end

    chrtr:jump()

    jumperBehavior(chrtr,p)
    -- Movement Section

    -- Attack Section
    if inRange then
        chrtr:damage(p)
        hit = true
    end
    -- Attack Section
end

-- This function use a coward statement to decide if keep on fight or cover up
-- could be implemented with some ai that require ingenious behavior
function commonSenseFunction(chrtr,indi)
    inRange = false
    evaluateDir(chrtr)

    if hideMe(chrtr,indi.min_health) then
        if runLikeBitch(chrtr,p) == 1 then
            chrtr:move(false)
        elseif runLikeBitch(chrtr,p) == 2 then
            chrtr:move(true)
        end
    else
        if chrtr:getHP() > indi.change_health and indi.behavior == 2 then --berseker
            bersekerBehavior(chrtr)
        elseif chrtr:getHP() < indi.change_health and indi.behavior == 2 then
            harBehavior(chrtr)
        elseif chrtr:getHP() > indi.min_helth and chrtr:getHP() < indi.final_change and indi.behavior == 2 then
            cowardBehavior(chrtr)
        end
    end

    if indi.recover then
        cowardBehavior(chrtr)
        chrtr:recover(indi.hp_per_tic)
    end

end