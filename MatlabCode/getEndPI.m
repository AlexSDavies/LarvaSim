function pis = getEndPI(name,nGroups,n)


	%% PI Index
	
	sides = zeros(nGroups*n,1);
	for i = 1:nGroups*n
		
		disp(i);
		
		rawData = dlmread(['../Data/data_' name  num2str(i)], ' ',2,0);
		finalXPos = rawData(end,8);
		
		% stats = getSimStats([name num2str(i)],0.1);
		
		if finalXPos < -10; sides(i) = 1; end;
		if finalXPos > 10; sides(i) = -1; end;
		
	end

	% Each row of sides is now a group
	sides = reshape(sides,nGroups,n);
	
	% Calculate PI for each row
	
	pis = sum(sides,2)./n;
	
