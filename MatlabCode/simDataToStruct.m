% Takes data in the form of a matrix of 'time perception angle...'
% and returns as a more useful struct
function data = simDataToStruct(rawData)

    data.time = rawData(:,1);
    data.perception = rawData(:,2);
    data.angle = rawData(:,3);
    data.headAngle = rawData(:,4);
    data.dAngle = rawData(:,5);
    data.bearing = rawData(:,6);


end