function angles = getPathAngles(path)


	for i = 1:length(path)-1
		
		p1 = path(i,:);
		p2 = path(i+1,:);
		
		angles(i) = atan2(p2(2) - p1(2), p2(1) - p1(1));
		
	end