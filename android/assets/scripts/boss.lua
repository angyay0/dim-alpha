--
-- Created by AIMOS STUDIO
-- User: Angel
-- Date: 30/10/2015
-- Time: 10:53 AM
--

require("basic")
require("officer")

function behavior(chrtr)
    p = chrtr:getPlay():getPlayer()
    indi = chrtr:getIndicators()
    inRange = false
    commonSenseFunction(chrtr,indi)

    if indi.spawner then
        if indi.spawnTimes < indi.limitSpawn then
            spawnEnemy(indi.spawnRate,indi.enemyToSpawn)
            chrtr:createEnemy()
            indi:addSpawn()
        end
    end
end

function spawnEnemy()

end

-- This is the minimalistic boss ai, basedn in pathfinder and
-- continuous state evaluation including sensing spawn areas
-- and player's health. This ia includes minions and officer spawning
-- This boss could be destroyed without taking care of phases
function minimalBossBehavior(chrtr)
end

-- This is an extension for the minimalistic boss ai. Includes everything that
-- could be found int minimalistic version of bosses, but this one
-- is the boss that you only could defeat in phases. That means, that you
-- have to cause damage in short periods of time, when its shield are down and minions
-- defeated.
function phasesBossBehavior(chrtr)
end

-- Prepare all your soul to be eaten by this gigantic boss
-- This is the result of a valid result for the markovRandomField method
-- Which indicates that the game is cheated in some way
function undefeatableBossBehavior(chrtr)
end

-- The special bosses, this ai can be transformed with an indicator for special occasions
-- like xmas, or anything special
function ultimateBossBehavior(chrtr)
end