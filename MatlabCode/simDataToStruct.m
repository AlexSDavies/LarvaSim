% Takes data in the form of a matrix of 'time perception angle...'
% and returns as a more useful struct
function data = simDataToStruct(rawData)

    data.time = rawData(:,1);
    data.perception = rawData(:,2);
    data.angle = rawData(:,3);
    data.headAngle = rawData(:,4);
    data.dAngle = rawData(:,5);
    data.bearing = rawData(:,6);
	data.odourVal = rawData(:,7);
	data.midPos = [rawData(:,8) rawData(:,9)];
	
	if(size(rawData,2) > 10)
		data.headPos = [rawData(:,10) rawData(:,11)];
		data.tailPos = [rawData(:,12) rawData(:,13)];
	end
end