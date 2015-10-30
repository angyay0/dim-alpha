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