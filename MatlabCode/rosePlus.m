
function rosePlus(data,color,alpha)

    f = rose(data,32);
    
    set(f,'Color','k','LineWidth',2);
	axis tight;
	%maxis = max(abs(axis));
	%axis([-maxis maxis -maxis maxis]);
    set(gca,'View',[90,90]);
    h = patch(get(f,'XData'),get(f,'YData'),color,'FaceAlpha',alpha);
	set(gca,'XTick',[],'YTick',[]);