function normAngle = normaliseAngle(angle)

	angle = mod(angle,2*pi);
		
	if (angle > pi)
		angle = -pi + mod(angle,pi);
	end
	
	if (angle < -pi)
		angle = pi + mod(angle,pi);
	end
		
	normAngle = angle;