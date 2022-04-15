//TMDB
const API_KEY = 'api_key=5c0b81e06313760df9f5da66e90090b8';
const BASE_URL = 'https://api.themoviedb.org/3';
const API_URL = BASE_URL+'/discover/movie?sort_by=popularity.desc&'+API_KEY;
const SEARCH_URL = BASE_URL+'/search/movie?'+API_KEY;
const IMG_URL = 'https://image.tmdb.org/t/p/w500/';

const main = document.getElementById('main');
const form = document.getElementById('form');
const search = document.getElementById('search');

getMovies(API_URL);

function getMovies(url){
	fetch(url).then(res=>res.json()).then(data=>{
		console.log(data.results);
		showMovies(data.results);
	})
}

function showMovies(data){
	main.innerHTML='';
	let cnt=0;
	data.forEach(movie=>{
		if(cnt==10)
			return;
		const movieDiv = document.createElement('div');
		movieDiv.classList.add('movie');
		
		const img = document.createElement('img');
		img.src = IMG_URL+movie.backdrop_path;
		const movieInfoDiv = document.createElement('div');
		movieInfoDiv.classList.add('movie-info');
		const overviewDiv = document.createElement('div');
		overviewDiv.classList.add('overview');
		
		movieDiv.appendChild(img);
		movieDiv.appendChild(movieInfoDiv);
		movieDiv.appendChild(overviewDiv);
		
		const movieInfoTitle = document.createElement('h3');
		movieInfoTitle.innerHTML=movie.original_title;
		const vote_average = document.createElement('span');
		vote_average.classList.add(getColor(movie.vote_average));
		vote_average.innerHTML =movie.vote_average;
		movieInfoDiv.appendChild(movieInfoTitle);
		movieInfoDiv.appendChild(vote_average);
		
		const overviewTitle = document.createElement('h3');
		overviewTitle.innerHTML = 'Overview';
		const p = document.createElement('p');
		p.innerHTML = movie.overview;
		overviewDiv.appendChild(overviewTitle);
		overviewDiv.appendChild(p);
		
		main.appendChild(movieDiv);
		cnt++;
	})
	
}

function getColor(vote){
	console.log(vote);
	if(vote>=8){
		return 'green'
	}else if(vote>=5){
		return 'orange'
	}else{
		return 'red'
	}
}

form.addEventListener('submit',(e)=>{
	e.preventDefault();
	
	const searchName=search.value;
	console.log(search.value);
	if(searchName){
		getMovies(SEARCH_URL+'&query='+searchName);
		search.value='';
	}else{
		getMovies(API_URL);
	}
})