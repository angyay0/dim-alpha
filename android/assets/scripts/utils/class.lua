--
-- Created by AIMOS STUDIO
-- User: Angel
-- Date: 29/10/2015
-- Time: 02:58 PM
--

local class = function(attr)
    local klass = attr or {}
    klass.__index = klass
    klass.__call = function(_,...) return klass:new(...) end
    function klass:new(...)
        local instance = setmetatable({}, klass)
        if klass.initialize then klass.initialize(instance, ...) end
        return instance
    end
    return setmetatable(klass,{__call = klass.__call})
end

return class

