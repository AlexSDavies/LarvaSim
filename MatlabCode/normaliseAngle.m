function normAngle = normaliseAngle(angle)

	angle = mod(angle,2*pi);
		
	angle(angle > pi) = -pi + mod(angle(angle > pi),pi);
	
	angle(angle < -pi) = pi - mod(abs(angle(angle < -pi)),pi);
		
	normAngle = angle;